package com.tenx.ms.storesmanagement.stock.converter;


import com.tenx.ms.storesmanagement.stock.domain.StockEntity;
import com.tenx.ms.storesmanagement.stock.rest.dto.StockDTO;
import org.springframework.stereotype.Component;

@Component
public abstract class StockConverter {

    public static StockEntity convertToStockEntity(StockDTO stockDTO) {
        StockEntity stockEntity = new StockEntity();
        stockEntity.setStoreId(stockDTO.getStoreId());
        stockEntity.setProductId(stockDTO.getProductId());
        stockEntity.setCount(stockDTO.getCount());
        return stockEntity;
    }

    public static StockDTO convertToStockDTO(StockEntity stockEntity) {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setStoreId(stockEntity.getStoreId());
        stockDTO.setProductId(stockEntity.getProductId());
        stockDTO.setCount(stockEntity.getCount());
        return stockDTO;
    }
}
