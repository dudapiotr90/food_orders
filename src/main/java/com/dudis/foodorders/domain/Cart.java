package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Set;

@With
@Value
@Builder
public class Cart {
    Integer cartId;
    Customer customer;
//    Set<OrderDetail> orderDetails;
    Set<OrderItem> orderItems;
}
