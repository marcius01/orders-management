package tech.skullprogrammer.auth.service;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import tech.skullprogrammer.auth.model.User;

import java.util.stream.Collectors;

public class ServiceUtilities {

    public static String generateToken(User user) {
        String token = Jwt.issuer("https://skullprogrammer.tech/issuer")
                .upn(user.getUsername())
                .groups(user.getRoles().stream().map(r->r.name()).collect(Collectors.toSet()))
                .claim(Claims.email.name(), user.getEmail())
                .sign();
        return token;
    }
}
