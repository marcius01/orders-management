package tech.skullprogrammer.products.utils;

import org.mapstruct.*;
import tech.skullprogrammer.products.model.Product;
import tech.skullprogrammer.products.model.dto.ProductRequestDTO;
import tech.skullprogrammer.products.model.dto.ProductResponseDTO;

import java.time.Instant;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface MapperUtils {

    @Mapping(source = "isActive", target = "active")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    Product toProduct(ProductRequestDTO dto, boolean isActive, Instant createdAt, Instant updatedAt);
    ProductResponseDTO toProductResponse(Product product);
    List<ProductResponseDTO> toProductResponse(List<Product> products);
    void updateProduct(ProductRequestDTO productRequestDTO, @MappingTarget Product product);
//    Pager<ProductResponseDTO> toProductResponse(Pager<Product> products);
}
