package tech.skullprogrammer.products.utils;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import tech.skullprogrammer.products.model.Product;
import tech.skullprogrammer.products.model.dto.ProductCreationRequestDTO;
import tech.skullprogrammer.products.model.dto.ProductResponseDTO;

import java.time.Instant;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface MapperUtils {

    @Mapping(source = "isActive", target = "active")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    Product toProduct(ProductCreationRequestDTO dto, boolean isActive, Instant createdAt, Instant updatedAt);
    ProductResponseDTO toProductResponse(Product product);
}
