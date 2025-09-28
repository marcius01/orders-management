package tech.skullprogrammer.artisanal.common.event;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RegisterForReflection
@ToString
public class OrderInitiatedEvent {
    private UUID eventId;
    private EEventType eventType;
    private Instant timestamp;
    private String version;
    private long correlationId;
    private Payload payload;

    @Getter
    @Setter
    @Builder
    @ToString
    public static class Payload {
        private long orderId;
        private long userId;
        private List<Item> items;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    public static class Item {
        private Long productId;
        private int quantity;
        private Double priceAtRequest;
    }
}