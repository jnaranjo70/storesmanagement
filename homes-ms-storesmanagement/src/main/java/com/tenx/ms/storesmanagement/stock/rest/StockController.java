package com.tenx.ms.storesmanagement.stock.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.storesmanagement.commons.rest.AbstractStoresManagementController;
import com.tenx.ms.storesmanagement.stock.rest.dto.StockDTO;
import com.tenx.ms.storesmanagement.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "stock", description = "Creates and updates stocks levels.")
@RestController
@RequestMapping(RestConstants.VERSION_ONE + "/stock")
public class StockController extends AbstractStoresManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "Gets the Stock level fot a product identified with productId i a given Store identified with storeId")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, the stock level is in the response"),
        @ApiResponse(code = 404, message = "Nor found, productId not available at Store identified with storeId is not found"),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = RequestMethod.GET)
    public StockDTO getStockByStoreIdAndByProductId(@ApiParam(name = "storeId", value = "The id to identify the Store to get") @PathVariable long storeId, @ApiParam(name = "productId", value = "The id to identify the product to get") @PathVariable long productId) {
        LOGGER.debug("Get Stock level for product with Id: {} within Store with id {}", productId,storeId);
        return stockService.getStockByStoreIdAndByProductId(storeId,productId).get();
    }

    @ApiOperation(value = "Creates a new Stock level entry")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, a new store is created"),
        @ApiResponse(code = 412, message = "Not valid input data."),
        @ApiResponse(code = 500, message = "Internal server error")})
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = {"/{storeId:\\d+}/{productId:\\d+}"}, method = {RequestMethod.POST,RequestMethod.PUT})
    public void createStock(@ApiParam(name = "stock", value = "The stock level to be created", required = true) @Validated @RequestBody StockDTO stock,
                                             @ApiParam(name = "storeId", value = "The Store ID ", required = true) @PathVariable Long storeId,
                                             @ApiParam(name = "productId", value = "TheProduct ID", required = true) @PathVariable Long productId) {
        LOGGER.debug("Creating new Stock level for product with ID: {} within Store withe ID: {}", productId,storeId);
        stock.setStoreId(storeId);
        stock.setProductId(productId);
        stockService.createStock(stock);
        LOGGER.debug("Stock level created");
    }

}
