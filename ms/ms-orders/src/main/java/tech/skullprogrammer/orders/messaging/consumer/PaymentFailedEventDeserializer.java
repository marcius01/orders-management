package tech.skullprogrammer.orders.messaging.consumer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import tech.skullprogrammer.artisanal.common.event.PaymentFailedEvent;

public class PaymentFailedEventDeserializer extends ObjectMapperDeserializer<PaymentFailedEvent> {

    public PaymentFailedEventDeserializer() {
        super(PaymentFailedEvent.class);
    }
}
