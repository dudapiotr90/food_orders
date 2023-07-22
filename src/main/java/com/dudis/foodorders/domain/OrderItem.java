package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class OrderItem {
    Integer orderItemId;
    Integer quantity;
    Order order;
    Food food;
}
