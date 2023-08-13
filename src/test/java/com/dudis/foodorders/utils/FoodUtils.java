package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.FoodRequestDTO;
import com.dudis.foodorders.domain.Food;

import java.math.BigDecimal;
import java.util.Set;

public class FoodUtils {
    public static Food someFood1() {
        return Food.builder()
            .foodId(1)
            .foodType("TEA")
            .price(BigDecimal.TEN)
            .build();
    }

    public static Food someFood2() {
        return Food.builder()
            .foodId(2)
            .foodType("COFFEE")
            .price(BigDecimal.valueOf(20))
            .build();
    }

    public static FoodRequestDTO someFoodRequest() {
        return FoodRequestDTO.builder()
            .foodId(12)
            .name("some food name")
            .quantity(BigDecimal.valueOf(3))
            .foodType("BREAD")
            .price(BigDecimal.valueOf(123))
            .build();
    }

    public static Set<Food> someFoods() {
        return Set.of(someFood1(), someFood2(), someFood3());
    }

    private static Food someFood3() {
        return Food.builder()
            .foodId(43)
            .foodType("ROLL")
            .price(BigDecimal.valueOf(123.44))
            .build();
    }
}
