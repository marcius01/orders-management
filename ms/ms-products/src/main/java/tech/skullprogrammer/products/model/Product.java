package tech.skullprogrammer.products.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    @Column(unique=true, nullable=false)
    private String sku;
    private Integer stockQuantity;
    private String imageUrl;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

}
