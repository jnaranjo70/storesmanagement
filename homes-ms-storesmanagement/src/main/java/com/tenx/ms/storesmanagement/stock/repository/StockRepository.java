package com.tenx.ms.storesmanagement.stock.repository;


import com.tenx.ms.storesmanagement.stock.domain.StockEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StockRepository extends PagingAndSortingRepository<StockEntity, Long> {


    Optional<StockEntity> findByStoreIdAndProductId(final Long storeId, final Long productId);

}

