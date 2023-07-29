package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderDetailEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;
import java.util.Set;

@With
@Value
@Builder
public class Cart {
    Integer cartId;
    Customer customer;
    Set<OrderDetail> orderDetails;
    List<OrderItem> orderItems;
}
