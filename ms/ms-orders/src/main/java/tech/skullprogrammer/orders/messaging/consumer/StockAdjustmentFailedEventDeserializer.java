package tech.skullprogrammer.orders.messaging.consumer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import tech.skullprogrammer.artisanal.common.event.StockAdjustmentFailedEvent;

public class StockAdjustmentFailedEventDeserializer extends ObjectMapperDeserializer<StockAdjustmentFailedEvent> {

    public StockAdjustmentFailedEventDeserializer() {
        super(StockAdjustmentFailedEvent.class);
    }
}
