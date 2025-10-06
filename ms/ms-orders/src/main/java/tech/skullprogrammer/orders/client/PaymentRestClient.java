package tech.skullprogrammer.orders.client;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import tech.skullprogrammer.orders.model.dto.PaymentRequestDTO;
import tech.skullprogrammer.orders.model.dto.PaymentResponseDTO;

@Path("/api/v1/payment")
@RegisterRestClient(configKey="payment-client")
@RegisterClientHeaders(ForwardAuthHeader.class)
public interface PaymentRestClient {

    @POST
    @Path("/secure/stripe-out")
    PaymentResponseDTO getStripePaymentUrl(PaymentRequestDTO requestDTO);

}
