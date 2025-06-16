package tech.skullprogrammer.auth.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.auth.model.dto.LoginDTO;
import tech.skullprogrammer.auth.model.dto.UserDTO;
import tech.skullprogrammer.auth.service.ServiceLogin;

@Slf4j
@Path( "auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestLogin {

    @Inject
    ServiceLogin serviceLogin;

    @POST()
    @Path("/login")
    @PermitAll
    public LoginDTO.Out login (@Valid LoginDTO.In loginData) {
        return serviceLogin.login(loginData.email(), loginData.password());
    }

    @POST()
    @Path("/register")
    @PermitAll
    public UserDTO.Out register (@Valid UserDTO.In userData) {
        return serviceLogin.register(userData);
    }
}
