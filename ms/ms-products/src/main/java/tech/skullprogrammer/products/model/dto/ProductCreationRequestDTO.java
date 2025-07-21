package tech.skullprogrammer.products.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Builder
public class ProductCreationRequestDTO {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;
    @NotBlank
    private String sku;
    @NotNull
    @Min(0)
    private Integer stockQuantity;
    private String imageUrl;

}
