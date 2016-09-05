package com.tenx.ms.storesmanagement.orders.service;

import com.tenx.ms.storesmanagement.commons.constants.OrderStatus;
import com.tenx.ms.storesmanagement.orders.converter.OrderConverter;
import com.tenx.ms.storesmanagement.orders.domain.OrderEntity;
import com.tenx.ms.storesmanagement.orders.repository.OrderRepository;
import com.tenx.ms.storesmanagement.orders.rest.dto.OrderDTO;
import com.tenx.ms.storesmanagement.orders.rest.dto.OrderResponseDTO;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Transactional (isolation= Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        orderDTO.setOrderId(UUID.randomUUID().toString());
        OrderEntity orderEntity = OrderConverter.convertToOrderEntity(orderDTO);
        orderEntity.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));
        orderEntity.setStatus(OrderStatus.CREATED);
        orderEntity = orderRepository.save(orderEntity);
        return new OrderResponseDTO(orderEntity.getOrderId(),orderEntity.getStatus());
    }
}


