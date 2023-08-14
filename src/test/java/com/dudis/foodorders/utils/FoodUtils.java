package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.dtos.FoodRequestDTO;
import com.dudis.foodorders.domain.Food;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class FoodUtils {

    public static List<Food> someFoodsList() {
        return List.of(someFood1(), someFood2(), someFood3());
    }

    public static List<FoodDTO> someFoodsListDTO() {
        return List.of(someFoodDTO1(), someFoodDTO2(), someFoodDTO3());
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

    public static Set<Food> someFoodsSet() {
        return Set.of(someFood1(), someFood2(), someFood3());
    }

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

    public static Food someFood3() {
        return Food.builder()
            .foodId(43)
            .foodType("ROLL")
            .price(BigDecimal.valueOf(123.44))
            .build();
    }


    public static FoodDTO someFoodDTO1() {
        return FoodDTO.builder()
            .foodId(1)
            .foodType("TEA")
            .price(BigDecimal.TEN)
            .build();
    }

    public static FoodDTO someFoodDTO2() {
        return FoodDTO.builder()
            .foodId(2)
            .foodType("COFFEE")
            .price(BigDecimal.valueOf(20))
            .build();
    }

    private static FoodDTO someFoodDTO3() {
        return FoodDTO.builder()
            .foodId(43)
            .foodType("ROLL")
            .price(BigDecimal.valueOf(123.44))
            .build();
    }
}
