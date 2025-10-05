package tech.skullprogrammer.orders.messaging.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import tech.skullprogrammer.artisanal.common.event.StockAdjustmentFailedEvent;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;
import tech.skullprogrammer.orders.exception.OrderError;
import tech.skullprogrammer.orders.messaging.producer.OrderStockReservetionFailedEventProducer;
import tech.skullprogrammer.orders.model.EOrderStatus;
import tech.skullprogrammer.orders.model.Order;
import tech.skullprogrammer.orders.repositiry.RepositoryOrder;


@Slf4j
@ApplicationScoped
public class StockAdjustmentFailedConsumer {

    @Inject
    OrderStockReservetionFailedEventProducer failMessageProducer;
    @Inject
    RepositoryOrder repositoryOrder;

    @Incoming("in-reservation-failed")
    public void consumeStockAdjustmentFailedEvent(StockAdjustmentFailedEvent event) {
        Order order = executeChangeState(event);
        failMessageProducer.produceFailMessage(order);
    }

    @Transactional
    protected Order executeChangeState(StockAdjustmentFailedEvent event) {
        Order order = repositoryOrder.findById(event.getPayload().getOrderId());
        if (order == null) {
            log.warn("Order not found: {}. No change state performed", event.getPayload().getOrderId());
            throw SkullResourceException.builder().error(OrderError.ORDER_NOT_FOUND).build();
        }
        order.setStatus(EOrderStatus.NO_STOCK);
        return order;
    }

}
