package tech.skullprogrammer.payments.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.payments.model.dto.PaymentRequestDTO;
import tech.skullprogrammer.payments.model.dto.PaymentResponseDTO;

@Path("/secure")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestSecurePayment {

    @POST
    @RolesAllowed( {"ROLE_USER", "ROLE_ADMIN"})
    @Path("/stripe-out")
    public PaymentResponseDTO startPayment(PaymentRequestDTO requestDTO) {
        log.info("STARTING PAYMENT {}", requestDTO);
        return PaymentResponseDTO.builder().paymentURL("Stripe request not implemented yet").build();
    }
}
