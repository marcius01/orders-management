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
public class StockAdjustmentSuccessEvent {

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
        private List<ReservedItem> reservedItems;
        private String reservationStatus;
    }

    @Getter
    @Setter
    @Builder
    public static class ReservedItem {
        private Long productId;
        private int reservedQuantity;
    }
}
