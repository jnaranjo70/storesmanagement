package com.tenx.ms.storesmanagement.orders.rest;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.storesmanagement.commons.rest.AbstractStoresManagementController;
import com.tenx.ms.storesmanagement.orders.rest.dto.OrderDTO;
import com.tenx.ms.storesmanagement.orders.rest.dto.OrderResponseDTO;
import com.tenx.ms.storesmanagement.orders.service.OrderService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "orders", description = "Creates and updates Orders.")
@RestController
@RequestMapping(RestConstants.VERSION_ONE + "/orders")
public class OrderController extends AbstractStoresManagementController {
    @Autowired
    private OrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);


    @ApiOperation(value = "Creates a new Order")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, a new Order is created"),
        @ApiResponse(code = 412, message = "Not valid input data."),
        @ApiResponse(code = 500, message = "Internal server error")})
    @RequestMapping(value = {"/{storeId:\\d+}"} , method = {RequestMethod.POST,RequestMethod.PUT})
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createProduct(@ApiParam(name = "storeId", value = "The id to identify the store  were to create the order") @PathVariable long storeId,
                                               @ApiParam(name = "order", value = "The order entity", required = true) @Validated @RequestBody OrderDTO order) {
        LOGGER.debug(String.format("Creating new Order in Store with ID: %d",storeId));
        order.setStoreId(storeId);
        OrderResponseDTO createdOrder = orderService.createOrder(order);
        LOGGER.debug(String.format("Order created with ID: %s and Status: %d", createdOrder.getOrderId(),createdOrder.getStatus()));
        return createdOrder;
    }
}
