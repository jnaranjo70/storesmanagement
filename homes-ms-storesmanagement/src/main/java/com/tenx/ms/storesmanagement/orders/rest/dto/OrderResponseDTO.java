package com.tenx.ms.storesmanagement.orders.rest.dto;


import io.swagger.annotations.ApiModelProperty;


public class OrderResponseDTO {

    @ApiModelProperty(value = "Order identificator", readOnly = true)
    private Long orderId;

    @ApiModelProperty(value = "Status of the Order")
    private int status;

    public OrderResponseDTO() {

    }

    public OrderResponseDTO(Long orderId, int status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
