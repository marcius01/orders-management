package tech.skullprogrammer.orders.messaging.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import tech.skullprogrammer.artisanal.common.event.EEventType;
import tech.skullprogrammer.artisanal.common.event.EventConstants;
import tech.skullprogrammer.artisanal.common.event.StockConfirmedEvent;
import tech.skullprogrammer.orders.model.Order;
import tech.skullprogrammer.orders.utils.MapperUtils;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class StockConfirmedEventProducer {


    @Inject
    @Channel("out-stock-reserved")
    Emitter<StockConfirmedEvent> emitter;

    public void produceStockConfirmedMessage(Order order) {
        log.debug("Sending stock confirmed event for order: {}", order.getId());
        StockConfirmedEvent event = StockConfirmedEvent.builder()
                .eventId(UUID.randomUUID())
                .version(EventConstants.VERSION)
                .timestamp(Instant.now())
                .eventType(EEventType.ORDER_INITIATED_EVENT)
                .payload(
                        StockConfirmedEvent.Payload.builder()
                                .orderId(order.getId())
                                .build()
                )
                .build();
        emitter.send(event);
        log.info("Send StockConfirmedEvent to Kafka for orderId {}", order.getId());
    }
}
