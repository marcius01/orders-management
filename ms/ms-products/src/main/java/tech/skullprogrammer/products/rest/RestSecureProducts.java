package tech.skullprogrammer.products.rest;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.framework.core.model.Pager;
import tech.skullprogrammer.products.model.dto.ProductRequestDTO;
import tech.skullprogrammer.products.model.dto.ProductResponseDTO;
import tech.skullprogrammer.products.model.dto.StockAdjustmentRequest;
import tech.skullprogrammer.products.service.ServiceProduct;

@Path("/secure")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestSecureProducts {

    @Inject
    SecurityIdentity identity;
    @Inject
    ServiceProduct serviceProduct;

    @POST
    @RolesAllowed({"ROLE_ADMIN"})
    public ProductResponseDTO create(@Valid ProductRequestDTO productCreation) {
        return serviceProduct.createProduct(productCreation);
    }

    @GET
    @Path("/admin")
    @RolesAllowed({"ROLE_ADMIN"})
    public Pager<ProductResponseDTO> admin(
            @QueryParam("page") @DefaultValue("0") @PositiveOrZero int page,
            @QueryParam("size") @DefaultValue("10") @Positive int size
    ) {
        return serviceProduct.getPaginatedProducts(page, size, null);
    }

    @GET
    @Path("/admin/{productId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public ProductResponseDTO product(@PathParam("productId") @PositiveOrZero Long productId) {
        return serviceProduct.getProduct(productId, null);
    }

    @PUT
    @Path("/{productId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public ProductResponseDTO update(@PathParam("productId") @PositiveOrZero Long productId, @Valid ProductRequestDTO productUpdate) {
        return serviceProduct.updateProduct(productId, productUpdate);
    }

    @DELETE
    @Path("/{productId}")
    @RolesAllowed({"ROLE_ADMIN"})
    public void softDelete(@PathParam("productId") Long productId){
        serviceProduct.softDelete(productId);

    }

    @POST
    @Path("/{productId}/stock")
    @RolesAllowed({"ROLE_ADMIN"})
    public ProductResponseDTO create(@PathParam("productId") Long productId, @Valid StockAdjustmentRequest stockAdjustmentRequest) {
        return serviceProduct.adjustStockQuantity(productId, stockAdjustmentRequest);
    }

//    @GET
//    @Path("/{productId}/snap")
//    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
//    public ProductResponseDTO getProductById(@PathParam("productId") Long productId) {
//
//    }
}
