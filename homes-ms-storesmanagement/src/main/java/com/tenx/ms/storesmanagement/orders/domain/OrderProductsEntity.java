package com.tenx.ms.storesmanagement.orders.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_products")
@IdClass(OrderProductsCompositeId.class)
public class OrderProductsEntity {

    @Id
    @Column(name = "order_id")
    private String orderId;
    @Id
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "count", columnDefinition = "int")
    private Long count;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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
