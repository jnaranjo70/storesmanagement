package com.tenx.ms.storesmanagement.order.converter;


import com.tenx.ms.storesmanagement.order.domain.OrderEntity;
import com.tenx.ms.storesmanagement.order.domain.OrderProductsEntity;
import com.tenx.ms.storesmanagement.order.rest.dto.OrderDTO;
import com.tenx.ms.storesmanagement.order.rest.dto.OrderProductsDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public abstract class OrderConverter {

    public static OrderEntity convertToOrderEntity(OrderDTO orderDTO) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderDTO.getOrderId());
        orderEntity.setStoreId(orderDTO.getStoreId());
        orderEntity.setOrderDate(orderDTO.getOrderDate());
        orderEntity.setStatus(orderDTO.getStatus());
        orderEntity.setFirstName(orderDTO.getFirstName());
        orderEntity.setLastname(orderDTO.getLastName());
        orderEntity.setEmail(orderDTO.getEmail());
        orderEntity.setPhone(orderDTO.getPhone());
        orderEntity.setProducts(orderDTO.getProducts().stream().map(orderProductDTO -> convertToOrderProductEntity(orderDTO.getOrderId(), orderProductDTO)).collect(Collectors.toList()));
        return orderEntity;
    }


    public static OrderProductsEntity convertToOrderProductEntity (String orderId, OrderProductsDTO orderProductsDTO) {
        OrderProductsEntity  orderProductsEntity = new OrderProductsEntity();
        orderProductsEntity.setOrderId(orderId);
        orderProductsEntity.setProductId(orderProductsDTO.getProductId());
        orderProductsEntity.setCount(orderProductsDTO.getCount());
        return orderProductsEntity;
    }


}
