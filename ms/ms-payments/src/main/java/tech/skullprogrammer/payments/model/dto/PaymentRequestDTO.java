package tech.skullprogrammer.payments.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@RegisterForReflection
@Builder
@Getter
@Setter
public class PaymentRequestDTO {
    private Long orderId;
    private Double amount;
}
