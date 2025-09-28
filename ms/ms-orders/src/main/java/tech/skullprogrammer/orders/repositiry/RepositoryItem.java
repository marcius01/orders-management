package tech.skullprogrammer.orders.repositiry;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.skullprogrammer.orders.model.Item;
import tech.skullprogrammer.orders.model.Order;

@ApplicationScoped
public class RepositoryItem implements PanacheRepository<Item> {

}
