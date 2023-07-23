package com.dudis.foodorders.domain;

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
    Restaurant restaurant;
    Set<Food> foods;
}
