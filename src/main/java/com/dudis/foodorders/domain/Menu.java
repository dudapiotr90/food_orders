package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
public class Menu {
    Integer menuId;
    String menuName;
    String description;
    Set<Food> foods;

    public Set<Food> getFoods() {
        return Objects.isNull(foods) ? new HashSet<>() : foods;
    }
}
