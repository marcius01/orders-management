package tech.skullprogrammer.orders.messaging.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import tech.skullprogrammer.artisanal.common.event.PaymentFailedEvent;
import tech.skullprogrammer.artisanal.common.event.PaymentSucceededEvent;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;
import tech.skullprogrammer.orders.exception.OrderError;
import tech.skullprogrammer.orders.model.EOrderStatus;
import tech.skullprogrammer.orders.model.Order;
import tech.skullprogrammer.orders.repositiry.RepositoryOrder;

@Slf4j
@ApplicationScoped
public class PaymentFailedConsumer {

    @Inject
    RepositoryOrder repositoryOrder;

    @Incoming("in-payment-failed")
    public void consumePaymentFailedEvent(PaymentFailedEvent event) {
        Order order = executeChangeState(event);
        log.debug("SEND Event for order id {} to notification MS to notify user", order.getId());
    }

    @Transactional
    protected Order executeChangeState(PaymentFailedEvent event) {
        Order order = repositoryOrder.findByIdOptional(event.getPayload().getOrderId()).orElseThrow(()->
                SkullResourceException.builder().error(OrderError.ORDER_NOT_FOUND).build());
        order.setStatus(EOrderStatus.PAYMENT_FAILED);
        return order;
    }
}
