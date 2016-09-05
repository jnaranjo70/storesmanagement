package com.tenx.ms.storesmanagement.orders.repository;


import com.tenx.ms.storesmanagement.orders.domain.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {


}

