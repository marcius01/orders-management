package tech.skullprogrammer.orders.messaging.consumer;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import tech.skullprogrammer.artisanal.common.event.PaymentSucceededEvent;

public class PaymentSucceededEventDeserializer extends ObjectMapperDeserializer<PaymentSucceededEvent> {

    public PaymentSucceededEventDeserializer() {
        super(PaymentSucceededEvent.class);
    }
}
