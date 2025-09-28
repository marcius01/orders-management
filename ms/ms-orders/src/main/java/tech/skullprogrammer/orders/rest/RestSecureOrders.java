package tech.skullprogrammer.orders.rest;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
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
    @RolesAllowed({"ROLE_ADMIN"})
//    @RolesAllowed({"ROLE_USER, ROLE_ADMIN"})
    public OrderResponseDTO create(@Valid OrderRequestDTO orderToCreate) {
        Long userId = securityUtils.getCurrentUserId();
        log.info("Creating order {} for user {}", orderToCreate, userId);
        return serviceOrder.placeOrder(orderToCreate, userId);
    }

}
