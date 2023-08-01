package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequestDTO {

    private Integer foodId;
    private String name;
    private BigDecimal quantity;
    private String foodType;
    private BigDecimal price;
    private String description;
}
