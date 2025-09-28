package tech.skullprogrammer.artisanal.common.event;


import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockConfirmedEvent {
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
    }

}
