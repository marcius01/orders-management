package tech.skullprogrammer.orders.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@RegisterForReflection
public class OrderItemRequestDTO {

    @NotNull
    @Positive
    private Long productId;
    @Min(1)
    private int quantity;
}
