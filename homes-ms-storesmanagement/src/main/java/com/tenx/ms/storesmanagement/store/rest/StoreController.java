package com.tenx.ms.storesmanagement.store.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.storesmanagement.commons.rest.AbstractStoresManagementController;
import com.tenx.ms.storesmanagement.store.rest.dto.StoreDTO;
import com.tenx.ms.storesmanagement.store.service.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "stores", description = "Creates and updates Stores. An Store holds an stock of products (see stock and product endpoints)")
@RestController
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController extends AbstractStoresManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "Gets the list of all Stores")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, a list of stores is in the response"),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(method = RequestMethod.GET)
    public List<StoreDTO> getStores(Pageable pageable) {
        LOGGER.debug("Get Stores");
        return storeService.getStores(pageable);
    }

    @ApiOperation(value = "Gets a Store identified with storeId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, the store identified with storeId is in the response"),
        @ApiResponse(code = 404, message = "Store identified with storeId is not found"),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = {"/{storeId:\\d+}"}, method = RequestMethod.GET)
    public StoreDTO getStoreById(@ApiParam(name = "storeId", value = "The id to identify the Store to get") @PathVariable long storeId) {
        LOGGER.debug("Get Store with Id: {}", storeId);
        return storeService.getStoreById(storeId).get();
    }

    @ApiOperation(value = "Creates a new Store")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, a new store is created"),
        @ApiResponse(code = 412, message = "Not valid input data."),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceCreated<Long> createStore(@ApiParam(name = "store", value = "The store entity", required = true) @Validated @RequestBody StoreDTO store) {
        LOGGER.debug("Creating new Store with Name: {}", store.getName());
        Long createdStoreId = storeService.createStore(store);
        LOGGER.debug("Store created with ID: {}", createdStoreId);
        return new ResourceCreated<Long>(createdStoreId);
    }

}
