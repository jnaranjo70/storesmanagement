package com.tenx.ms.storesmanagement.product.repository;


import com.tenx.ms.storesmanagement.product.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {

    Optional<ProductEntity> findOneByProductId(final Long productId);

    Optional<ProductEntity> findByStoreIdAndProductId(final Long storeId, final Long productId);

    Page<ProductEntity> findAll(Pageable pageable);

    Page<ProductEntity> findAllByStoreId(final Long storeId , Pageable pageable);

}

