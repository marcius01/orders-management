package tech.skullprogrammer.orders.messaging.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
//import org.eclipse.microprofile.reactive.messaging.Incoming;
//import tech.skullprogrammer.artisanal.common.event.OrderInitiatedEvent;
//import tech.skullprogrammer.artisanal.common.event.StockAdjustmentFailedEvent;
//import tech.skullprogrammer.framework.core.exception.GenericErrorPayload;
//import tech.skullprogrammer.framework.core.exception.SkullResourceException;
//import tech.skullprogrammer.products.exception.ProductError;
//import tech.skullprogrammer.products.model.StockReservation;
//import tech.skullprogrammer.products.repository.RepositoryProduct;
//import tech.skullprogrammer.products.repository.RepositoryStockReservation;


@Slf4j
@ApplicationScoped
public class StockAdjustmentFailedConsumer {
//
//    @Inject
//    RepositoryStockReservation repositoryStockReservation;
//    @Inject
//    RepositoryProduct repositoryProduct;
//    @Inject
//    StockAdjustmentFailedProducer stockMessageProducer;
//
//    @Incoming("orders-in")
//    public void consumeOrderCreatedEvent(OrderInitiatedEvent event) {
//        Map<Long, OrderInitiatedEvent.Item> itemMap = event.getPayload().getItems().stream().collect(Collectors.toMap(
//                OrderInitiatedEvent.Item::getProductId, e -> e,
//                (e1, e2) -> e1));
//        Long orderId = event.getPayload().getOrderId();
//        List<StockReservation> reservations = repositoryStockReservation.findByOrderIdAndProductIds(orderId, new ArrayList<>(itemMap.keySet()));
//        List<Long> itemIdsRes = reservations.stream().map(StockReservation::getProductId).toList();
//        itemIdsRes.forEach(itemMap::remove);
//        try {
//            decrementStocks(event, itemMap);
//        } catch (SkullResourceException e) {
//            StockAdjustmentFailedEvent.FailedItem failedItem = (StockAdjustmentFailedEvent.FailedItem) ((GenericErrorPayload)e.getPayload()).getInfo().get("failed item");
//            stockMessageProducer.produceStockMessage(orderId, List.of(failedItem), "Stock adjustment failed");
//        }
//    }
//
//    @Transactional
//    private void decrementStocks(OrderInitiatedEvent event, Map<Long, OrderInitiatedEvent.Item> itemMap) {
//        for (Long productId : itemMap.keySet()) {
//            int decrementStock = itemMap.get(productId).getQuantity();
//            StockReservation stockReservation = StockReservation.builder()
//                    .orderId(event.getPayload().getOrderId())
//                    .productId(productId).createdAt(Instant.now())
//                    .quantity(decrementStock)
//                    .build();
//            repositoryStockReservation.persist(stockReservation);
//            int updated = repositoryProduct.updateStock(productId, decrementStock);
//            if (updated < 1) {
//                StockAdjustmentFailedEvent.FailedItem failedItem = StockAdjustmentFailedEvent.FailedItem.builder()
//                        .productId(productId)
//                        .requestedQuantity(decrementStock)
//                        .build();
//                throw SkullResourceException.builder()
//                        .error(ProductError.INSUFFICIENT_STOCK)
//                        .payload(GenericErrorPayload.builder().putInfo("failed item", failedItem).build())
//                        .build();
//            }
//        }
//    }
}
