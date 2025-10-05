package tech.skullprogrammer.artisanal.common.event;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSucceededEvent {
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
        private String paymentId;
        private BigDecimal amountPaid;
        private String currency;
        private Instant paymentTimestamp;
    }

    public static PaymentSucceededEvent create(Long orderId, String paymentId,
                                               BigDecimal amountPaid, String currency,
                                               Instant paymentTimestamp, String correlationId) {
        return PaymentSucceededEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("PaymentSucceededEvent")
                .timestamp(Instant.now())
                .version("1.0")
                .correlationId(correlationId)
                .payload(Payload.builder()
                        .orderId(orderId)
                        .paymentId(paymentId)
                        .amountPaid(amountPaid)
                        .currency(currency)
                        .paymentTimestamp(paymentTimestamp)
                        .build())
                .build();
    }
}