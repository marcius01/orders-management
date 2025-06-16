package tech.skullprogrammer.auth.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.skullprogrammer.auth.model.User;

@ApplicationScoped
public class RepositoryUser implements PanacheRepository<User> {

    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }
    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

}
