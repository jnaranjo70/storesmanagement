package com.tenx.ms.storesmanagement.stock.domain;

import javax.persistence.*;

@Entity
@Table(name = "stock")
@IdClass(StockCompositeId.class)
public class StockEntity {

    @Id
    @Column(name = "store_id")
    private Long storeId;
    @Id
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "count")
    private Long count;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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
