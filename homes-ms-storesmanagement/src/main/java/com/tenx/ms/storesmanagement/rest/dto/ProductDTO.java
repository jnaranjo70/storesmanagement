package com.tenx.ms.storesmanagement.rest.dto;


import com.tenx.ms.commons.validation.constraints.DollarAmount;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class ProductDTO {

    @ApiModelProperty(value = "Product identificator", readOnly = true)
    private Long productId;

    @ApiModelProperty(value = "Store identificator",readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Product name")
    @NotNull
    private String name;

    @ApiModelProperty("Product description")
    @NotNull
    private String description;

    @ApiModelProperty("Product SKU")
    @NotNull
    private String sku;

    @ApiModelProperty("Product Price")
    @NotNull
    @DollarAmount
    private BigDecimal price;

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

    public String getName() {
        return name;
    }

    public void  setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public  void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String toString() {
        return "Product name: " + name + ", Product id: " + productId;
    }
}
