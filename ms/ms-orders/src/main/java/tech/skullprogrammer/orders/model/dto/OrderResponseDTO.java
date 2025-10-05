package tech.skullprogrammer.orders.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class OrderResponseDTO {

    private Long id;
    private Long userId;
    private List<OrderItemResponseDTO> items;
    private BigDecimal totalAmount;
    private String status;
    private String currency;
    private Long shippingAddressId;
    private Long billingAddressId;
    private Instant createdAt;
    private Instant updatedAt;

}
