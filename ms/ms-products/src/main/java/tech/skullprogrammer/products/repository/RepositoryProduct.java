package tech.skullprogrammer.products.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import tech.skullprogrammer.framework.core.model.Pager;
import tech.skullprogrammer.products.model.Product;
import tech.skullprogrammer.products.model.dto.ProductResponseDTO;
import tech.skullprogrammer.products.utils.MapperUtils;

import java.util.List;

@ApplicationScoped
public class RepositoryProduct implements PanacheRepository<Product> {

    public Product findBySku(String sku) {
        return find("sku", sku).firstResult();
    }

    public Pager<Product> findPaginatedProducts(int page, int size, Boolean isActive) {
        PanacheQuery<Product> query = switch (isActive) {
            case null -> find("ORDER BY createdAt DESC");
            default -> find("active = :isActive ORDER BY createdAt DESC", Parameters.with("isActive", isActive));
        };
        find("active = :isActive ORDER BY createdAt DESC", Parameters.with("isActive", isActive))
                .page(Page.of(page, size));
        long total = query.count();
        return Pager.<Product>builder()
                .result(query.list())
                .size(size)
                .page(page)
                .total(total)
                .build();
    }

    public Product findById(Long productId, Boolean isActive) {
        PanacheQuery<Product> query = switch (isActive) {
            case null -> find("id", productId).firstResult();
            default -> find("id = :id AND active = :active", Parameters.with("id", productId).and("active", isActive));
        };
        return query.firstResult();
    }
}
