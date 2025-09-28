package tech.skullprogrammer.orders.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.skullprogrammer.framework.core.exception.GenericErrorPayload;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;
import tech.skullprogrammer.orders.client.ProductRestClient;
import tech.skullprogrammer.orders.exception.OrderError;
import tech.skullprogrammer.orders.messaging.producer.OrderInitiatedEventProducer;
import tech.skullprogrammer.orders.model.ECurrency;
import tech.skullprogrammer.orders.model.EOrderStatus;
import tech.skullprogrammer.orders.model.Item;
import tech.skullprogrammer.orders.model.Order;
import tech.skullprogrammer.orders.model.dto.OrderItemRequestDTO;
import tech.skullprogrammer.orders.model.dto.OrderRequestDTO;
import tech.skullprogrammer.orders.model.dto.OrderResponseDTO;
import tech.skullprogrammer.orders.model.dto.ProductResponseDTO;
import tech.skullprogrammer.orders.repositiry.RepositoryOrder;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class ServiceOrder {

    @RestClient
    ProductRestClient productRestClient;
    @Inject
    RepositoryOrder repositoryOrder;
    @Inject
    OrderInitiatedEventProducer orderInitiatedEventProducer;



    public OrderResponseDTO placeOrder(OrderRequestDTO orderToCreate, Long userId) {
        log.info("Received request to create order {}", orderToCreate);
        List<Long> productIds = orderToCreate.getItems().stream().map(OrderItemRequestDTO::getProductId).toList();
        List<ProductResponseDTO> products = productRestClient.getProductListByIds(productIds);
        Order order = composeOrder(orderToCreate, products, userId);
        persistOrder(order);
        orderInitiatedEventProducer.produceOrderInitiatedMessage(order);
        return null;
    }

    @Transactional
    protected void persistOrder(Order order) {
        repositoryOrder.persist(order);
    }

    private Order composeOrder(OrderRequestDTO orderToCreate, List<ProductResponseDTO> productDTOs, Long userId) {
        Map<Long, ProductResponseDTO> productsMap = productDTOs.stream().collect(Collectors.toMap(ProductResponseDTO::getId, p -> p));
        List<Long> idsNotReturned = orderToCreate.getItems().stream()
                .map(OrderItemRequestDTO::getProductId)
                .filter(i -> productsMap.get(i) == null)
                .toList();
        if (!idsNotReturned.isEmpty()) throwNoProductException(idsNotReturned);
        Order order = generateOrder(orderToCreate, userId);
        for (OrderItemRequestDTO itemRequest : orderToCreate.getItems()) {
            ProductResponseDTO product = productsMap.get(itemRequest.getProductId());
            Item item = generateItem(itemRequest, order, product);
            order.addItem(item);
        }
        return order;
    }

    private static Item generateItem(OrderItemRequestDTO itemRequest, Order order, ProductResponseDTO product) {
        return Item.builder()
                .order(order)
                .productId(product.getId())
                .quantity(itemRequest.getQuantity())
                .priceAtPurchaseSnapshot(product.getPrice())
                .productNameSnapshot(product.getName())
                .build();
    }

    private Order generateOrder(OrderRequestDTO orderToCreate, Long userId) {
        return Order.builder()
                .userId(userId)
                .status(EOrderStatus.PENDING)
                .shippingAddress(orderToCreate.getShippingAddress())
                .billingAddress(orderToCreate.getBillingAddress())
                .currency(ECurrency.EUR)
                .createdAt(Instant.now())
                .build();
    }

    private void throwNoProductException(List<Long> idsNotReturned) {
        throw SkullResourceException.builder().error(OrderError.PRODUCT_NOT_FOUND)
                .payload(GenericErrorPayload.builder().putInfo("Product ids", idsNotReturned).build())
                .build();
    }

}
