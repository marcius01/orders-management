package tech.skullprogrammer.orders.utils;

import io.quarkus.logging.Log;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import tech.skullprogrammer.framework.core.exception.GenericErrorPayload;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;
import tech.skullprogrammer.orders.exception.OrderError;

@ApplicationScoped
public class SecurityUtils {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    JsonWebToken jwt;

    public Long getCurrentUserId() {
        String upn = null;
        try {
            upn = securityIdentity.getPrincipal().getName();
            return Long.parseLong(upn);
        } catch (NumberFormatException e) {
            Log.error("Invalid user ID format in JWT token: " + upn, e);
            throw SkullResourceException.builder()
                    .error(OrderError.UNAUTHORIZED)
                    .payload(GenericErrorPayload.builder().putInfo("UPN", upn).build())
                    .build();
        }
    }

    public String getCurrentUserEmail() {
        try {
            return jwt.getClaim(Claims.email.name()).toString();
        } catch (Exception e) {
            Log.warn("Could not extract email from JWT", e);
            return null; // Email potrebbe essere opzionale
        }
    }

    public boolean isAdmin() {
        return securityIdentity.hasRole("ROLE_ADMIN");
    }
}