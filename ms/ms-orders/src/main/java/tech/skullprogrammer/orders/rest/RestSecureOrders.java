package tech.skullprogrammer.orders.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.orders.model.dto.OrderPaymentResponseDTO;
import tech.skullprogrammer.orders.model.dto.OrderRequestDTO;
import tech.skullprogrammer.orders.model.dto.OrderResponseDTO;
import tech.skullprogrammer.orders.service.ServiceOrder;
import tech.skullprogrammer.orders.utils.SecurityUtils;

@Path("/secure")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestSecureOrders {

    @Inject
    SecurityUtils securityUtils;
    @Inject
    ServiceOrder serviceOrder;

    @POST
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public OrderResponseDTO create(@Valid OrderRequestDTO orderToCreate) {
        Long userId = securityUtils.getCurrentUserId();
        log.debug("Creating order {} for user {}", orderToCreate, userId);
        return serviceOrder.placeOrder(orderToCreate, userId);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public OrderResponseDTO delete(@PathParam("id") Long orderToDelete) {
        Long userId = securityUtils.getCurrentUserId();
        log.debug("Virtual Delete of the order {} for user {}", orderToDelete, userId);
        return serviceOrder.virtualDeleteOrder(orderToDelete, userId, securityUtils.isAdmin());
    }

    @GET
    @Path("/{id}/payment-session")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public OrderPaymentResponseDTO getPaymentSession(@PathParam("id") Long orderId) {
        Long userId = securityUtils.getCurrentUserId();
        log.debug("Get payment session for order {} by user {}", orderId, userId);
        return serviceOrder.getPaymentSession(orderId, userId, securityUtils.isAdmin());
    }

}
