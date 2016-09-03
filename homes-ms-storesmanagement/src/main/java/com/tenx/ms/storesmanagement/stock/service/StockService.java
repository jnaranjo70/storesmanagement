package com.tenx.ms.storesmanagement.stock.service;

import com.tenx.ms.storesmanagement.stock.converter.StockConverter;
import com.tenx.ms.storesmanagement.stock.domain.StockEntity;
import com.tenx.ms.storesmanagement.stock.repository.StockRepository;
import com.tenx.ms.storesmanagement.stock.rest.dto.StockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Optional<StockDTO> getStockByStoreIdAndByProductId(Long storeId, Long productId) {
        return stockRepository.findByStoreIdAndProductId(storeId, productId).map(stockEntity -> StockConverter.convertToStockDTO(stockEntity));
    }

    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public void createStock(StockDTO stockDTO) {
        StockEntity stockEntity = StockConverter.convertToStockEntity(stockDTO);
        stockEntity = stockRepository.save(stockEntity);
    }
}


