package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder
public class Food {
    Integer foodId;
    String name;
    String description;
    BigDecimal price;
    String foodType;
    String foodImagePath;
    Menu menu;
}
