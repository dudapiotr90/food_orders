package com.dudis.foodorders.services.utils;

import com.dudis.foodorders.domain.Food;

import java.math.BigDecimal;

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
}
