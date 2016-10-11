package com.tenx.ms.storesmanagement.stock.rest.dto;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


public class StockDTO {

    @ApiModelProperty(value = "Product identificator", readOnly = true)
    private Long productId;

    @ApiModelProperty(value = "Store identificator",readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Count of the Product in the given Store")
    @NotNull
    private Long count;


     public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public Long getStoreId() {
        return storeId;
    }

    public  void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCount() {
        return count;
    }

    public void  setCount(Long count) {
        this.count = count;
    }

    public String toString() {
        return "Store id: " + storeId + ", Product id: " + productId + "-> Count: " + count;
    }
}
