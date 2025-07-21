package tech.skullprogrammer.auth.rest;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.auth.exception.AuthError;
import tech.skullprogrammer.auth.model.dto.UserDTO;
import tech.skullprogrammer.auth.service.ServiceLogin;
import tech.skullprogrammer.framework.core.exception.GenericErrorPayload;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;

@Slf4j
@Path("secure/details")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestDetails {

    @Inject
    ServiceLogin serviceLogin;
    @Inject
    SecurityIdentity identity;

    @GET()
    @Path("/me")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public UserDTO.Out me() {
        String userId = identity.getPrincipal().getName();
        try {
            return serviceLogin.findUser(Long.parseLong(userId));
        } catch (NumberFormatException e) {
            throw SkullResourceException.builder()
                    .error(AuthError.BAD_PRINCIPAL)
                    .payload(GenericErrorPayload.builder().putInfo("userId", userId).build())
                    .build();
        }

    }

    @GET()
    @Path("/{userId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public UserDTO.Out me(@PathParam("userId") Long userId) {
        return serviceLogin.findUser(userId);
    }
}
