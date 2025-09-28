package tech.skullprogrammer.products.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import tech.skullprogrammer.products.model.StockReservation;

import java.util.List;

@ApplicationScoped
public class RepositoryStockReservation implements PanacheRepository<StockReservation> {

    public List<StockReservation> findByOrderIdAndProductIds(long orderId, List<Long> productsId) {
        return find("orderId = :orderId and productId in :productIds",
                Parameters.with("orderId", orderId).and("productIds", productsId))
                .list();
    }
}
