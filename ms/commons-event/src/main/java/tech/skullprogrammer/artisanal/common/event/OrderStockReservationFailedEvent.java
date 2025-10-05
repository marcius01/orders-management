package tech.skullprogrammer.artisanal.common.event;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@RegisterForReflection
public class OrderStockReservationFailedEvent {

    private UUID eventId;
    private EEventType eventType;
    private Instant timestamp;
    private String version;
    private long correlationId;
    private Payload payload;

    @Getter
    @Setter
    @Builder
    public static class Payload {
        private long orderId;
        private List<FailedItem> failedItems;
        private String reason;
    }

    @Getter
    @Setter
    @Builder
    public static class FailedItem {
        private Long productId;
        private int requestedQuantity;
    }
}
