package tech.skullprogrammer.auth.rest;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.auth.exception.AuthError;
import tech.skullprogrammer.auth.model.dto.LoginDTO;
import tech.skullprogrammer.auth.model.dto.UserDTO;
import tech.skullprogrammer.auth.service.ServiceLogin;
import tech.skullprogrammer.framework.core.exception.GenericErrorPayload;
import tech.skullprogrammer.framework.core.exception.ISkullError;
import tech.skullprogrammer.framework.core.exception.SkullResourceException;

@Slf4j
@Path("public")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestLogin {

    @Inject
    ServiceLogin serviceLogin;

    @POST()
    @Path("/login")
    @PermitAll
    public LoginDTO.Out login(@Valid LoginDTO.In loginData) {
        return serviceLogin.login(loginData.email(), loginData.password());
    }

    @POST()
    @Path("/register")
    @PermitAll
    public UserDTO.Out register(@Valid UserDTO.In userData) {
        return serviceLogin.register(userData);
    }
}
