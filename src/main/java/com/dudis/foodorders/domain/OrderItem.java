package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class OrderItem {

    Integer orderItemId;
    Integer quantity;
    OrderDetail orderDetail;
    Food food;
}
