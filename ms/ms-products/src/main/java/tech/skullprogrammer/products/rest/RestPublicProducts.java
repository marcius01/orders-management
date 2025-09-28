package tech.skullprogrammer.products.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import tech.skullprogrammer.framework.core.model.Pager;
import tech.skullprogrammer.products.messaging.StockAdjustmentFailedProducer;
import tech.skullprogrammer.products.model.dto.ProductResponseDTO;
import tech.skullprogrammer.products.service.ServiceProduct;

import java.util.List;


@Path("/public")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestPublicProducts {

    @Inject
    ServiceProduct serviceProduct;

    @GET
    @PermitAll
    public Pager<ProductResponseDTO> paginatedActiveProduct(
            @QueryParam("page") @DefaultValue("0") @PositiveOrZero int page,
            @QueryParam("size") @DefaultValue("10") @Positive int size
    ) {
        return serviceProduct.getPaginatedProducts(page, size, true );
    }

    @GET
    @Path("/{productId}")
    @PermitAll
    public ProductResponseDTO product(@PathParam("productId") @PositiveOrZero Long productId) {
        return serviceProduct.getProduct(productId, true);
    }

    @POST
    @Path("/")
    @PermitAll
    public List<ProductResponseDTO> product(@NotNull @NotEmpty List<Long> productIds) {
        return serviceProduct.getProducts(productIds, true);
    }

}
