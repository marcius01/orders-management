package tech.skullprogrammer.products.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.framework.core.exception.GenericErrorPayload;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;
import tech.skullprogrammer.framework.core.model.Pager;
import tech.skullprogrammer.products.exception.ProductError;
import tech.skullprogrammer.products.model.Product;
import tech.skullprogrammer.products.model.dto.ProductRequestDTO;
import tech.skullprogrammer.products.model.dto.ProductResponseDTO;
import tech.skullprogrammer.products.model.dto.StockAdjustmentRequest;
import tech.skullprogrammer.products.repository.RepositoryProduct;
import tech.skullprogrammer.products.utils.MapperUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
public class ServiceProduct {

    @Inject
    RepositoryProduct repositoryProduct;
    @Inject
    MapperUtils mapper;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productCreation) {
        Product productOnDB = repositoryProduct.findBySku(productCreation.getSku());
        if (productOnDB != null) {
            throw SkullResourceException.builder()
                    .error(ProductError.PRODUCT_ALREADY_EXISTS)
                    .payload(GenericErrorPayload.builder().putInfo("sku", productCreation.getSku()).build())
                    .build();
        }
        Product product = mapper.toProduct(productCreation, true, LocalDateTime.now().toInstant(ZoneOffset.UTC), null);
        repositoryProduct.persist(product);
        return mapper.toProductResponse(product);
    }


    public Pager<ProductResponseDTO> getPaginatedProducts(@PositiveOrZero int page, @Positive int size, Boolean isActive) {
        Pager<Product> products = repositoryProduct.findPaginatedProducts(page, size, isActive);
        return Pager.<ProductResponseDTO>builder()
                .result(mapper.toProductResponse(products.getResult()))
                .size(products.getSize())
                .page(products.getPage())
                .total(products.getTotal())
                .build();
    }

    public ProductResponseDTO getProduct(@PositiveOrZero Long productId, Boolean isActive) {
        return mapper.toProductResponse(repositoryProduct.findById(productId, isActive));
    }

    public List<ProductResponseDTO> getProducts(@NotNull @NotEmpty List<Long> productIds, Boolean isActive) {
        return mapper.toProductResponse(repositoryProduct.findByIds(productIds, isActive));
    }

    @Transactional
    public ProductResponseDTO updateProduct(@PositiveOrZero Long productId, ProductRequestDTO productUpdate) {
        Product product = getProducts(productId);
        Product productSKU = repositoryProduct.findBySku(productUpdate.getSku());
        if (productSKU != null && !productSKU.getId().equals(productId)) {
            throw SkullResourceException.builder()
                    .error(ProductError.PRODUCT_ALREADY_EXISTS)
                    .payload(GenericErrorPayload.builder().putInfo("sku", productUpdate.getSku()).build())
                    .build();
        }
        mapper.updateProduct(productUpdate, product);
        log.debug("Product updated: {}", product);
        return mapper.toProductResponse(product);
    }

    @Transactional
    public void softDelete(Long productId) {
        log.info("Soft deleting product with id: {}", productId);
        Product product = getProducts(productId);
        product.setActive(false);
        log.info("Product soft deleted: {}", product);
    }

    @Transactional
    public ProductResponseDTO adjustStockQuantity(Long productId, @Valid StockAdjustmentRequest stockAdjustmentRequest) {
        Product product = getProducts(productId);
        if (stockAdjustmentRequest.quantity() < 0 && (product.getStockQuantity() + stockAdjustmentRequest.quantity()) < 0) {
            throw SkullResourceException.builder()
                    .error(ProductError.INSUFFICIENT_STOCK)
                    .payload(GenericErrorPayload.builder().putInfo("available stock", product.getStockQuantity()).build())
                    .build();
        }
        product.setStockQuantity(product.getStockQuantity() + stockAdjustmentRequest.quantity());
        return mapper.toProductResponse(product);
    }


    private Product getProducts(Long productId) {
        Product product = repositoryProduct.findById(productId);
        if (product == null) {
            throw SkullResourceException.builder().error(ProductError.PRODUCT_NOT_FOUND).build();
        }
        return product;
    }
}
