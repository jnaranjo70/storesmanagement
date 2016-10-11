package com.tenx.ms.storesmanagement.order.domain;

import java.io.Serializable;

/**
 *
 */
public class OrderProductsCompositeId implements Serializable {

    protected String orderId;
    protected Long productId;

    public OrderProductsCompositeId() { }

    public OrderProductsCompositeId(String orderId, Long productId) {
         this.orderId=orderId;
         this.productId=productId;
    }

}
