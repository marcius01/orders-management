package tech.skullprogrammer.orders.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> items;
    private Long userId;
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;
    @Enumerated(EnumType.STRING)
    private ECurrency currency;
    private String shippingAddress;
    private String billingAddress;
    private Instant createdAt;
    private Instant updatedAt;

    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }
    public void calcuteAndSaveTotalAmount() {
        this.totalAmount = calculateTotalAmount();
    }

    public Double calculateTotalAmount() {
        if (items == null || items.isEmpty()) {
            return 0.0;
        }
        return items.stream().mapToDouble(i->i.getPriceAtPurchaseSnapshot()*i.getQuantity()).sum();
    }

    @PrePersist
    @PreUpdate
    private void updateTotalAmount() {
        calcuteAndSaveTotalAmount();
    }

}
