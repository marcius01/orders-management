package tech.skullprogrammer.payments.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.payments.model.dto.PaymentRequestDTO;
import tech.skullprogrammer.payments.model.dto.PaymentResponseDTO;

@Path("/public")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestPublicPayment {

    @GET
    @Path("/health")
    @PermitAll
    public Response health() {
        log.info("MS-PAYMENT IS ON");
        return Response.ok("It Works").build();
    }
}
