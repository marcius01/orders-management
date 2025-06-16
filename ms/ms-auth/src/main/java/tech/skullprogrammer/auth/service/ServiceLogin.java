package tech.skullprogrammer.auth.service;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import tech.skullprogrammer.auth.exception.AuthError;
import tech.skullprogrammer.auth.model.ERole;
import tech.skullprogrammer.auth.model.User;
import tech.skullprogrammer.auth.model.dto.LoginDTO;
import tech.skullprogrammer.auth.model.dto.UserDTO;
import tech.skullprogrammer.auth.repository.RepositoryUser;
import tech.skullprogrammer.auth.utility.MapperUtils;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;

import java.util.Set;

@ApplicationScoped
public class ServiceLogin {

    private static final int BCRYPT_COST = 12;

    @Inject
    RepositoryUser repositoryUser;
    @Inject
    MapperUtils mapperUtils;

    public LoginDTO.Out login(String email, String password) {
        User user = repositoryUser.findByEmail(email);
        boolean authenticated = false;
        if (user == null) {
            // for timing attack prevention
            BcryptUtil.bcryptHash("dummy", BCRYPT_COST);
            throw SkullResourceException.builder().error(AuthError.UNAUTHORIZED).build();
        }
        authenticated = BcryptUtil.matches(password, user.getPassword());
        if (!authenticated) {throw SkullResourceException.builder().error(AuthError.UNAUTHORIZED).build();}
        return new LoginDTO.Out(ServiceUtilities.generateToken(user));
    }

    @Transactional
    public UserDTO.Out register(UserDTO.In userData) {
        User userOnDb = repositoryUser.findByEmail(userData.email());
        if (userOnDb != null) {
            throw SkullResourceException.builder().error(AuthError.EMAIL_ALREADY_EXISTS).build();
        }
        String passwordHash = BcryptUtil.bcryptHash(userData.password());
        User user = mapperUtils.toUser(userData, passwordHash, Set.of(ERole.ROLE_USER));
        repositoryUser.persist(user);
        return mapperUtils.toUserDTO(user);
    }
}
