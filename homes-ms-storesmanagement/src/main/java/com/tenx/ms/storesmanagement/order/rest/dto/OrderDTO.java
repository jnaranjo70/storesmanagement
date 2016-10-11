package com.tenx.ms.storesmanagement.order.rest.dto;


import com.tenx.ms.commons.validation.constraints.Email;
import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


public class OrderDTO {

    @ApiModelProperty(value = "Order identificator", readOnly = true)
    private String orderId;

    @ApiModelProperty(value = "Store identificator", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Order Date",readOnly = true)
    private Date orderDate;

    @ApiModelProperty(value = "Status of the Order")
    private int status;

    @ApiModelProperty(value = "Name of the customer")
    @NotNull
    private String firstName;

    @ApiModelProperty(value = "Last name of the customer")
    @NotNull
    private String lastName;

    @ApiModelProperty(value = "Email of the customer")
    @NotNull
    @Email
    private String email;

    @ApiModelProperty(value = "Phone of the customer")
    @NotNull
    @PhoneNumber
    private String phone;

    @ApiModelProperty(value = "products in the order")
    @NotNull
    private List<OrderProductsDTO> products;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderProductsDTO> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProductsDTO> products) {
        this.products = products;
    }

    public String toString() {
        return "Order id: " + orderId;
    }
}
