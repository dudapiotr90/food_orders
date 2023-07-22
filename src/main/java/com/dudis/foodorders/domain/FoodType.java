package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Set;

@With
@Value
@Builder
public class FoodType {

    Integer foodTypeId;
    String name;
    Set<Food> foods;
}
