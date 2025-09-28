package tech.skullprogrammer.orders.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne()
    private Order order;
    private Long productId;
    private String productNameSnapshot;
    private Double priceAtPurchaseSnapshot;
    private int quantity;

}
