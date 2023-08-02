package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import lombok.*;

import java.math.BigDecimal;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Integer orderItemId;
    private BigDecimal quantity;
    private CartDTO cart;
    private FoodDTO food;
    private OrderDTO order;
    private BigDecimal totalCost;
}
