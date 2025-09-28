package tech.skullprogrammer.orders.messaging.consumer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import tech.skullprogrammer.artisanal.common.event.StockAdjustmentSuccessEvent;

public class StockAdjustmentSuccessEventDeserializer extends ObjectMapperDeserializer<StockAdjustmentSuccessEvent> {

    public StockAdjustmentSuccessEventDeserializer() {
        super(StockAdjustmentSuccessEvent.class);
    }
}
