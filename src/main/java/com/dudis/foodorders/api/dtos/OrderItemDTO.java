package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
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
    CartDTO cart;
    FoodDTO food;
    OrderDTO order;
}
