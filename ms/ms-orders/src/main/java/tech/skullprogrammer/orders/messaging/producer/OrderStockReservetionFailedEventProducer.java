package tech.skullprogrammer.orders.messaging.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import tech.skullprogrammer.artisanal.common.event.EEventType;
import tech.skullprogrammer.artisanal.common.event.EventConstants;
import tech.skullprogrammer.artisanal.common.event.OrderInitiatedEvent;
import tech.skullprogrammer.artisanal.common.event.OrderStockReservationFailedEvent;
import tech.skullprogrammer.orders.model.Order;
import tech.skullprogrammer.orders.utils.MapperUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class OrderStockReservetionFailedEventProducer {


    @Inject
    @Channel("out-stock-failed")
    Emitter<OrderStockReservationFailedEvent> emitter;
    @Inject
    MapperUtils mapper;

    public void produceFailMessage(Order order) {
        List<OrderStockReservationFailedEvent.FailedItem> items = mapper.toFailEventItems(order.getItems());
        log.debug("Sending order initiated event: {}", items);
        OrderStockReservationFailedEvent event = OrderStockReservationFailedEvent.builder()
                .eventId(UUID.randomUUID())
                .version(EventConstants.VERSION)
                .timestamp(Instant.now())
                .eventType(EEventType.ORDER_INITIATED_EVENT)
                .payload(
                        OrderStockReservationFailedEvent.Payload.builder()
                                .orderId(order.getId())
                                .failedItems(items)
                                .build()
                )
                .build();
        emitter.send(event);
        log.info("Send OrderStockReservationFailedEvent to Kafka for orderId {}", order.getId());
    }
}
