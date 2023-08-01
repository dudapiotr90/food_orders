package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder
public class OrderItem {

    Integer orderItemId;
    BigDecimal quantity;
    Cart cart;
    Food food;
    Order order;
}
