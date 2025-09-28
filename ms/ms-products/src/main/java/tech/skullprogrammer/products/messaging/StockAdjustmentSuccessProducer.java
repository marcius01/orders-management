package tech.skullprogrammer.products.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import tech.skullprogrammer.artisanal.common.event.EEventType;
import tech.skullprogrammer.artisanal.common.event.EventConstants;
import tech.skullprogrammer.artisanal.common.event.StockAdjustmentSuccessEvent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class StockAdjustmentSuccessProducer {

    @Inject
    @Channel("out-stock-success")
    private Emitter<StockAdjustmentSuccessEvent> emitter;

    public void produceStockMessage(long orderId, List<StockAdjustmentSuccessEvent.ReservedItem> reservedItems, String reason) {
        StockAdjustmentSuccessEvent event = StockAdjustmentSuccessEvent.builder()
                .eventId(UUID.randomUUID())
                .version(EventConstants.VERSION)
                .timestamp(Instant.now())
                .eventType(EEventType.STOCK_ADJUSTMENT_FAILED_EVENT)
                .payload(
                        StockAdjustmentSuccessEvent.Payload.builder()
                                .orderId(orderId)
                                .reservedItems(reservedItems)
                                .reservationStatus(reason)
                                .build()
                )
                .build();
        emitter.send(event);
        log.info("Send StockAdjustmentSuccessEvent to Kafka for orderId {}", orderId);
    }

}
