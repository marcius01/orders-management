package tech.skullprogrammer.products.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.skullprogrammer.products.model.Product;

@ApplicationScoped
public class RepositoryProduct implements PanacheRepository<Product> {

    public Product findBySku(String sku) {
        return find("sku", sku).firstResult();
    }
}
