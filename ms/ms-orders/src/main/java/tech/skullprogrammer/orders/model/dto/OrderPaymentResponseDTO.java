package tech.skullprogrammer.orders.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class OrderPaymentResponseDTO {
    private String paymentURL;
}
