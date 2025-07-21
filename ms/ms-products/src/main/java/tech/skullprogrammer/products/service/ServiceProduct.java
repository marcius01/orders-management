package tech.skullprogrammer.products.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import tech.skullprogrammer.framework.core.exception.GenericErrorPayload;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;
import tech.skullprogrammer.products.exception.ProductError;
import tech.skullprogrammer.products.model.Product;
import tech.skullprogrammer.products.model.dto.ProductCreationRequestDTO;
import tech.skullprogrammer.products.model.dto.ProductResponseDTO;
import tech.skullprogrammer.products.repository.RepositoryProduct;
import tech.skullprogrammer.products.utils.MapperUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
public class ServiceProduct {

    @Inject
    RepositoryProduct repositoryProduct;
    @Inject
    MapperUtils mapper;

    @Transactional
    public ProductResponseDTO createProduct(ProductCreationRequestDTO productCreation) {
        Product productOnDB = repositoryProduct.findBySku(productCreation.getSku());
        if(productOnDB != null) {
            throw SkullResourceException.builder()
                    .error(ProductError.PRODUCT_ALREADY_EXISTS)
                    .payload(GenericErrorPayload.builder().putInfo("sku", productCreation.getSku()).build())
                    .build();
        }
        Product product = mapper.toProduct(productCreation, true, LocalDateTime.now().toInstant(ZoneOffset.UTC), null );
        repositoryProduct.persist(product);
        return mapper.toProductResponse(product);
    }
}
