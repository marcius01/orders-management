package tech.skullprogrammer.products.rest;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.products.model.Product;
import tech.skullprogrammer.products.model.dto.ProductCreationRequestDTO;
import tech.skullprogrammer.products.model.dto.ProductResponseDTO;
import tech.skullprogrammer.products.service.ServiceProduct;

import java.util.List;

@Slf4j
@Path("/secure")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestSecureProducts {

    @Inject
    SecurityIdentity identity;
    @Inject
    ServiceProduct serviceProduct;

    @GET
    @PermitAll
    public List<Product> list() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @POST
    @RolesAllowed({"ROLE_ADMIN"})
    public ProductResponseDTO create(ProductCreationRequestDTO productCreation) {
        return serviceProduct.createProduct(productCreation);
    }
}
