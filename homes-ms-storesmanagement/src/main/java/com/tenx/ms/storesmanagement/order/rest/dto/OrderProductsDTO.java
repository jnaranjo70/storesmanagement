package com.tenx.ms.storesmanagement.order.rest.dto;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


public class OrderProductsDTO {

    @ApiModelProperty(value = "Product identificator")
    @NotNull
    private Long productId;

    @ApiModelProperty(value = "Product count")
    @NotNull
    private Long count;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
