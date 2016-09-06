package com.tenx.ms.storesmanagement.store.rest.dto;


import com.tenx.ms.commons.validation.constraints.PhoneNumber;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;


public class StoreDTO {
    @ApiModelProperty(value = "Store identificator", readOnly = true)
    private Long storeId;

    @ApiModelProperty(value = "Store name")
    @NotNull
    private String name;

    @ApiModelProperty("Store address")
    private String address;

    @ApiModelProperty("Store phone number")
    @PhoneNumber
    private String phone;

    @ApiModelProperty("Other notes")
    private String notes;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String toString() {
        return "Strore name: " + name + ", Store id: " + storeId;
    }
}
