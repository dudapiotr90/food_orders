package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;

@With
@Value
@Builder
public class OrderDetail {
    Integer orderDetailId;
    Customer customer;
    Order order;
    List<OrderItem> orderItems;
}
