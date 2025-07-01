package tech.skullprogrammer.auth.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import tech.skullprogrammer.auth.model.User;

import java.util.stream.Collectors;

@ApplicationScoped
public class ServiceUtilities {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    public String generateToken(User user) {
        return Jwt.issuer(issuer)
                .upn(user.getId().toString())
                .groups(user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                .claim(Claims.email.name(), user.getEmail())
                .sign();
    }
}
