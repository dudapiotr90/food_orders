package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Set;

@With
@Value
@Builder
public class Menu {
    Integer menuId;
    String menuName;
    String description;
    Restaurant local;
    Set<Food> foods;
}
