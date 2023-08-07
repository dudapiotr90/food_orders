package com.dudis.foodorders.api.dtos;

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
