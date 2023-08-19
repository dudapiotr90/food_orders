package com.dudis.foodorders.infrastructure.spoonacular;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.TreeSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealMap {
    private TreeSet<Meal> meals;
    private Nutrients nutrients;
}
