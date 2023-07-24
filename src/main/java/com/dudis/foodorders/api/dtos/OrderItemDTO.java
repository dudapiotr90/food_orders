package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    Integer orderItemId;
    Integer quantity;
    Order order;
    FoodDTO food;
}
