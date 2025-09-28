package tech.skullprogrammer.products.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import tech.skullprogrammer.artisanal.common.event.OrderInitiatedEvent;
import tech.skullprogrammer.artisanal.common.event.StockAdjustmentFailedEvent;
import tech.skullprogrammer.artisanal.common.event.StockAdjustmentSuccessEvent;
import tech.skullprogrammer.framework.core.exception.GenericErrorPayload;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;
import tech.skullprogrammer.products.exception.ProductError;
import tech.skullprogrammer.products.model.StockReservation;
import tech.skullprogrammer.products.repository.RepositoryProduct;
import tech.skullprogrammer.products.repository.RepositoryStockReservation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
public class StockMessageConsumer {

    @Inject
    RepositoryStockReservation repositoryStockReservation;
    @Inject
    RepositoryProduct repositoryProduct;
    @Inject
    StockAdjustmentFailedProducer stockMessageFailedProducer;
    @Inject
    StockAdjustmentSuccessProducer stockMessageSuccessProducer;


    @Incoming("in-stock-request")
    public void consumeOrderCreatedEvent(OrderInitiatedEvent event) {
        log.debug("Received Order Created Event: {}", event);
        Map<Long, OrderInitiatedEvent.Item> itemMap = event.getPayload().getItems().stream().collect(Collectors.toMap(
                OrderInitiatedEvent.Item::getProductId, e -> e,
                (e1, e2) -> e1));
        Long orderId = event.getPayload().getOrderId();
        List<StockReservation> reservations = getStockReservations(orderId, itemMap);
        List<Long> itemIdsRes = reservations.stream().map(StockReservation::getProductId).toList();
        itemIdsRes.forEach(itemMap::remove);
        try {
            decrementStocks(event, itemMap);
            List<StockAdjustmentSuccessEvent.ReservedItem> reserv = itemMap.entrySet().stream().map(e-> {
                OrderInitiatedEvent.Item item = e.getValue();
                return StockAdjustmentSuccessEvent.ReservedItem.builder().reservedQuantity(item.getQuantity()).productId(item.getProductId()).build();
            }). toList();
            stockMessageSuccessProducer.produceStockMessage(orderId, reserv, "SUCCESS");
        } catch (SkullResourceException e) {
            StockAdjustmentFailedEvent.FailedItem failedItem = (StockAdjustmentFailedEvent.FailedItem) ((GenericErrorPayload)e.getPayload()).getInfo().get("failed item");
            stockMessageFailedProducer.produceStockMessage(orderId, List.of(failedItem), "Stock adjustment failed");
        }
    }

    @Transactional
    protected List<StockReservation> getStockReservations(Long orderId, Map<Long, OrderInitiatedEvent.Item> itemMap) {
        List<StockReservation> reservations = repositoryStockReservation.findByOrderIdAndProductIds(orderId, new ArrayList<>(itemMap.keySet()));
        return reservations;
    }

    @Transactional
    protected void decrementStocks(OrderInitiatedEvent event, Map<Long, OrderInitiatedEvent.Item> itemMap) {
        for (Long productId : itemMap.keySet()) {
            log.debug("Decrementing stock for product {}", productId);
            int decrementStock = itemMap.get(productId).getQuantity();
            log.debug("Stock to decrement: {}", decrementStock);
            StockReservation stockReservation = StockReservation.builder()
                    .orderId(event.getPayload().getOrderId())
                    .productId(productId).createdAt(Instant.now())
                    .quantity(decrementStock)
                    .build();
            repositoryStockReservation.persist(stockReservation);
            int updated = repositoryProduct.updateStock(productId, decrementStock);
            log.debug("Updated product: {}", updated);
            if (updated < 1) {
                StockAdjustmentFailedEvent.FailedItem failedItem = StockAdjustmentFailedEvent.FailedItem.builder()
                        .productId(productId)
                        .requestedQuantity(decrementStock)
                        .build();
                throw SkullResourceException.builder()
                        .error(ProductError.INSUFFICIENT_STOCK)
                        .payload(GenericErrorPayload.builder().putInfo("failed item", failedItem).build())
                        .build();
            }
        }
    }
}
