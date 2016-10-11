package com.tenx.ms.storesmanagement.stock.domain;

import java.io.Serializable;

/**
 *
 */
public class StockCompositeId implements Serializable {

    protected Long storeId;
    protected Long productId;

    public StockCompositeId() { }

    public StockCompositeId(Long storeId,  Long productId) {
         this.storeId=storeId;
         this.productId=productId;
    }

}
