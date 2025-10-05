package tech.skullprogrammer.artisanal.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {
    private String eventId;
    private String eventType;
    private Instant timestamp;
    private String version;
    private String correlationId;
    private Payload payload;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Payload {
        private Long orderId;
        private String reason;
    }

    // Factory di convenienza
    public static PaymentFailedEvent create(Long orderId, String reason, String correlationId) {
        return PaymentFailedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("PaymentFailedEvent")
                .timestamp(Instant.now())
                .version("1.0")
                .correlationId(correlationId)
                .payload(Payload.builder()
                        .orderId(orderId)
                        .reason(reason)
                        .build())
                .build();
    }
}