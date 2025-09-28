package tech.skullprogrammer.orders.model.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class OrderItemResponseDTO {

    private Long productId;
    private String productNameSnapshot;
    private BigDecimal priceAtPurchaseSnapshot;
    private int quantity;

}
