package tech.skullprogrammer.products.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import tech.skullprogrammer.artisanal.common.event.EEventType;
import tech.skullprogrammer.artisanal.common.event.EventConstants;
import tech.skullprogrammer.artisanal.common.event.StockAdjustmentFailedEvent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class StockAdjustmentFailedProducer {


    @Inject
    @Channel("out-stock-failure")
    Emitter<StockAdjustmentFailedEvent> emitter;

    public void produceStockMessage(long orderId, List<StockAdjustmentFailedEvent.FailedItem> failedItems, String reason) {
        StockAdjustmentFailedEvent event = StockAdjustmentFailedEvent.builder()
                .eventId(UUID.randomUUID())
                .version(EventConstants.VERSION)
                .timestamp(Instant.now())
                .eventType(EEventType.STOCK_ADJUSTMENT_FAILED_EVENT)
                .payload(
                        StockAdjustmentFailedEvent.Payload.builder()
                                .orderId(orderId)
                                .failedItems(failedItems)
                                .reason(reason)
                                .build()
                )
                .build();
        emitter.send(event);
        log.info("Send StockAdjustmentFailedEvent to Kafka for orderId {}", orderId);
    }
}
