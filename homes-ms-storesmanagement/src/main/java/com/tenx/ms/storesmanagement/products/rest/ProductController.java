package com.tenx.ms.storesmanagement.products.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.storesmanagement.commons.rest.AbstractStoresManagementController;
import com.tenx.ms.storesmanagement.products.rest.dto.ProductDTO;
import com.tenx.ms.storesmanagement.products.service.ProductService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "product", description = "Creates and updates Products.")
@RestController
@RequestMapping(RestConstants.VERSION_ONE + "/products")
public class ProductController extends AbstractStoresManagementController {
    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);


    @ApiOperation(value = "Gets the list of all Products of a store")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, a list of products is in the response"),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = {"/{storeId:\\d+}"} , method = RequestMethod.GET)
    public List<ProductDTO> getProducts(@ApiParam(name = "storeId", value = "The id to identify the store from were to get the product list") @PathVariable long storeId, Pageable pageable) {
        LOGGER.debug("Get Products fopr store: " + storeId);
        return productService.getProductsByStoreId(storeId, pageable);
    }

    @ApiOperation(value = "Gets a Product identified with productId within a Store identified with storeID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, the product identified with productId is in the response"),
        @ApiResponse(code = 404, message = "Product identified with productId is not found"),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = RequestMethod.GET)
    public ProductDTO getProductById(@ApiParam(name = "storeId", value = "The id to identify the store from were to get the product") @PathVariable long storeId,
                                     @ApiParam(name = "productId", value = "The id to identify the Product to get") @PathVariable long productId) {
        LOGGER.debug(String.format("Get Product with Id: %s, from Store with id: %s", productId, storeId));
        return productService.getProductByStoreIdAndById(storeId, productId).get();
    }

    @ApiOperation(value = "Creates a new Product")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, a new product is created"),
        @ApiResponse(code = 412, message = "Not valid input data."),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = {"/{storeId:\\d+}"} , method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceCreated<Long> createProduct(@ApiParam(name = "storeId", value = "The id to identify the store  were to create the product") @PathVariable long storeId,
                                               @ApiParam(name = "product", value = "The product entity", required = true) @Validated @RequestBody ProductDTO product) {
        LOGGER.debug(String.format("Creating new Product with Name: %s in Store with ID: %d", product.getName(), storeId));
        product.setStoreId(storeId);
        Long createdProductId = productService.createProduct(product);
        LOGGER.debug(String.format("Product created with ID: %d in Store with ID: %d", createdProductId,storeId));
        return new ResourceCreated<Long>(createdProductId);
    }

}
