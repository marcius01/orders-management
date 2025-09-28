package tech.skullprogrammer.orders.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import tech.skullprogrammer.orders.model.dto.ProductResponseDTO;

import java.util.List;

@Path("/api/v1/products")
@RegisterRestClient(configKey="product-client")
public interface ProductRestClient {
//
//    @GET
//    @Path("/secure/product/{productId}/snap")
//    ProductResponseDTO getProductById(@PathParam("productId") Long productId);
//
//    @POST
//    @Path("/secure/product/snap")
//    List<ProductResponseDTO> getProducts(List<Integer> productIds);

    @GET
    @Path("/public/{productId}")
    ProductResponseDTO getProductById(@PathParam("productId") Long productId);

    @POST
    @Path("/public/")
    List<ProductResponseDTO> getProductListByIds(List<Long> productIds);
}
