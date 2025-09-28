package tech.skullprogrammer.orders.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@RegisterForReflection
public class OrderRequestDTO {

    @NotNull
    @NotEmpty
    private List<OrderItemRequestDTO> items;
    private String shippingAddress;
    private String billingAddress;

}
