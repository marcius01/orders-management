package tech.skullprogrammer.products.config;

import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
//@ApplicationScoped
public class HeaderAuthenticationMechanism implements HttpAuthenticationMechanism {

    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_ROLES_HEADER = "X-User-Roles";

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        String userId = context.request().getHeader(USER_ID_HEADER);
        String roles = context.request().getHeader(USER_ROLES_HEADER);
        if (userId == null || userId.isBlank() || roles == null || roles.isBlank()) {
            log.debug("Missing authentication headers");
            return Uni.createFrom().nullItem();
        }
        log.debug("Authentication headers found: userId={}, roles={}", userId, roles);
        QuarkusPrincipal principal = new QuarkusPrincipal(userId);
        Set<String> rolesSet = new HashSet<>(Arrays.asList(roles.split(",")));

        // Costruiamo la SecurityIdentity che Quarkus capisce
        SecurityIdentity identity = QuarkusSecurityIdentity.builder()
                .setPrincipal(principal)
                .addRoles(rolesSet)
                .build();

        return Uni.createFrom().item(identity);
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        // Questo metodo viene chiamato se l'autenticazione fallisce.
        // Restituiamo un 401 Unauthorized.
        return Uni.createFrom().item(new ChallengeData(401, "WWW-Authenticate", "Bearer"));
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        // Indichiamo che questo meccanismo non gestisce credenziali standard.
        return new HashSet<>();
    }
}