package com.tenx.ms.storesmanagement.order.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.storesmanagement.commons.rest.AbstractStoresManagementController;
import com.tenx.ms.storesmanagement.order.rest.dto.OrderDTO;
import com.tenx.ms.storesmanagement.order.rest.dto.OrderResponseDTO;
import com.tenx.ms.storesmanagement.order.service.OrderService;
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

@Api(value = "orders", description = "Creates and updates Orders.")
@RestController
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController extends AbstractStoresManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Creates a new Order")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, a new Order is created"),
        @ApiResponse(code = 412, message = "Not valid input data."),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = {"/{storeId:\\d+}"} , method = {RequestMethod.POST,RequestMethod.PUT})
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createProduct(@ApiParam(name = "storeId", value = "The id to identify the store  were to create the order") @PathVariable long storeId,
                                               @ApiParam(name = "order", value = "The order entity", required = true) @Validated @RequestBody OrderDTO order) {
        LOGGER.debug("Creating new Order in Store with ID: {}",storeId);
        order.setStoreId(storeId);
        OrderResponseDTO createdOrder = orderService.createOrder(order);
        LOGGER.debug("Order created with ID: {} and Status: {}", createdOrder.getOrderId(),createdOrder.getStatus());
        return createdOrder;
    }
}
