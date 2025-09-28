package tech.skullprogrammer.orders.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String sku;
    private Integer stockQuantity;
    private String imageUrl;
    private boolean isActive;

}
