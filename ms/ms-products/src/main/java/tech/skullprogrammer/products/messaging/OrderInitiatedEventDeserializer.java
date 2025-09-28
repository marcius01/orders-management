package tech.skullprogrammer.products.messaging;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import tech.skullprogrammer.artisanal.common.event.OrderInitiatedEvent;

public class OrderInitiatedEventDeserializer extends ObjectMapperDeserializer<OrderInitiatedEvent> {

    public OrderInitiatedEventDeserializer() {
        super(OrderInitiatedEvent.class);
    }
}
