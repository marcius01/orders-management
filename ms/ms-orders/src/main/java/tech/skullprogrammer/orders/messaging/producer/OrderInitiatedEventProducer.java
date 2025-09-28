package tech.skullprogrammer.orders.messaging.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import tech.skullprogrammer.artisanal.common.event.EEventType;
import tech.skullprogrammer.artisanal.common.event.EventConstants;
import tech.skullprogrammer.artisanal.common.event.OrderInitiatedEvent;
import tech.skullprogrammer.orders.model.Order;
import tech.skullprogrammer.orders.utils.MapperUtils;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class OrderInitiatedEventProducer {


    @Inject
    @Channel("orders-out")
    Emitter<OrderInitiatedEvent> emitter;
    @Inject
    MapperUtils mapper;

    public void produceOrderInitiatedMessage(Order order) {
        List<OrderInitiatedEvent.Item> items = mapper.toEventItems(order.getItems());
        log.debug("Sending order initiated event: {}", items);
        OrderInitiatedEvent event = OrderInitiatedEvent.builder()
                .eventId(UUID.randomUUID())
                .version(EventConstants.VERSION)
                .timestamp(Instant.now())
                .eventType(EEventType.ORDER_INITIATED_EVENT)
                .payload(
                        OrderInitiatedEvent.Payload.builder()
                                .orderId(order.getId())
                                .userId(order.getUserId())
                                .items(items)
                                .build()
                )
                .build();
        emitter.send(event);
        log.info("Send OrderInitiatedEvent to Kafka for orderId {}", order.getId());
    }
}
