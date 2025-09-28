package tech.skullprogrammer.orders.messaging.consumer;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import tech.skullprogrammer.artisanal.common.event.StockAdjustmentSuccessEvent;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;
import tech.skullprogrammer.orders.exception.OrderError;
import tech.skullprogrammer.orders.messaging.producer.StockConfirmedEventProducer;
import tech.skullprogrammer.orders.model.EOrderStatus;
import tech.skullprogrammer.orders.model.Order;
import tech.skullprogrammer.orders.repositiry.RepositoryOrder;

public class StockAdjustmentSuccessConsumer {

    @Inject
    RepositoryOrder repositoryOrder;
    @Inject
    StockConfirmedEventProducer stockConfirmedEventProducer;


    @Incoming("in-reservation-success")
    public void consume(StockAdjustmentSuccessEvent successEvent) {
        Order order = findOrder(successEvent);
        if (order == null) {
            throw SkullResourceException.builder().error(OrderError.ORDER_NOT_FOUND).build();
        }
        order.setStatus(EOrderStatus.STOCK_RESERVED);
        stockConfirmedEventProducer.produceStockConfirmedMessage(order);
    }

    @Transactional
    protected Order findOrder(StockAdjustmentSuccessEvent successEvent) {
        return repositoryOrder.findById(successEvent.getPayload().getOrderId());
    }
}
