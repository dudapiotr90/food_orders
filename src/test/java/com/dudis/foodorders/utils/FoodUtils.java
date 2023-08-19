package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.dtos.FoodRequestDTO;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class FoodUtils {

    public static List<Food> someFoodsList() {
        return List.of(someFood1(), someFood2(), someFood3());
    }

    public static List<FoodEntity> someFoodEntities() {
        return List.of(someFoodEntity1(), someFoodEntity2(), someFoodEntity3());
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

    public static Set<FoodDTO> someFoodsSetDTO() {
        return Set.of(someFoodDTO1(), someFoodDTO2(), someFoodDTO3());
    }

    public static Food someFood1() {
        return Food.builder()
            .foodId(1)
            .name("Tomatoes")
            .description("ripen in the sun")
            .foodType("TEA")
            .price(BigDecimal.TEN)
            .foodImagePath("some/image/path")
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
            .name("Tomatoes")
            .description("ripen in the sun")
            .foodId(1)
            .foodType("TEA")
            .price(BigDecimal.TEN)
            .build();
    }

    public static FoodDTO someFoodDTO2() {
        return FoodDTO.builder()
            .name("small black")
            .foodId(2)
            .foodType("COFFEE")
            .price(BigDecimal.valueOf(20))
            .build();
    }

    private static FoodDTO someFoodDTO3() {
        return FoodDTO.builder()
            .name("traditional")
            .foodId(43)
            .foodType("ROLL")
            .price(BigDecimal.valueOf(123.44))
            .build();
    }

    public static FoodEntity someFoodEntity1() {
        return FoodEntity.builder()
            .foodId(1)
            .name("Tomatoes")
            .description("ripen in the sun")
            .foodType("TEA")
            .price(BigDecimal.TEN)
            .foodImagePath("some/image/path")
            .build();
    }

    public static FoodEntity someFoodEntity2() {
        return FoodEntity.builder()
            .foodId(2)
            .name("small black")
            .foodType("COFFEE")
            .price(BigDecimal.valueOf(20))
            .build();
    }

    public static FoodEntity someFoodEntity3() {
        return FoodEntity.builder()
            .name("traditional")
            .foodId(43)
            .foodType("ROLL")
            .price(BigDecimal.valueOf(123.44))
            .build();
    }


    public static FoodEntity someFoodEntity4() {
        return FoodEntity.builder()
            .foodId(43)
            .menu(MenuUtils.someMenuEntity4())
            .foodType("ROLL")
            .price(BigDecimal.valueOf(123.44))
            .build();
    }

    public static Set<FoodEntity> someSetOfFoodEntities() {
        return Set.of(someFoodEntity1(), someFoodEntity2(), someFoodEntity3());
    }

//    public static FoodEntity foodForJpaTest1() {
//        return FoodEntity.builder()
//            .name("Finlandia")
//            .price(new BigDecimal("20.00"))
//            .foodType("DRINK")
//            .build();
//    }
//
//    public static FoodEntity foodForJpaTest2() {
//        return FoodEntity.builder()
//            .name("sophia")
//            .price(new BigDecimal("17.00"))
//            .foodType("WINE")
//            .build();
//    }
//
//    public static FoodEntity foodForJpaTest3() {
//        return FoodEntity.builder()
//            .name("bulka tarta")
//            .price(new BigDecimal("45.00"))
//            .foodType("APPETIZER")
//            .build();
//    }
//
//    public static List<FoodEntity> foodListForJpaTest() {
//        return List.of(foodForJpaTest1(), foodForJpaTest2(), foodForJpaTest3());
//    }
}