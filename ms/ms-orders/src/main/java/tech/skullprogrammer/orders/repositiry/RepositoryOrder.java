package tech.skullprogrammer.orders.repositiry;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import tech.skullprogrammer.orders.model.Order;

import java.util.Optional;

@ApplicationScoped
public class RepositoryOrder implements PanacheRepository<Order> {

    public Optional<Order> findByIdAndUserId(Long orderId, Long userId) {
        return find("id = :orderId AND userId = :userId", Parameters.with("orderId", orderId).and("userId", userId)).firstResultOptional();
    }
}
