package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;

import java.util.Set;

public class MenuUtils {
    public static MenuDTO someMenuDTO() {
        return MenuDTO.builder()
            .menuName("some menu name")
            .foods(FoodUtils.someFoodsSetDTO())
            .build();
    }
    public static Menu someMenu1() {
        return Menu.builder()
            .menuId(1)
            .menuName("some menu name")
            .description("some description")
            .foods(FoodUtils.someFoodsSet())
            .build();
    }

    public static Menu someMenu2() {
        return Menu.builder()
            .menuName("another menu name")
            .menuId(123)
            .foods(Set.of(FoodUtils.someFood1()))
            .build();
    }

    public static Menu someMenu3() {
        return Menu.builder()
            .menuName("and another menu name")
            .menuId(4)
            .foods(Set.of())
            .build();
    }


    public static MenuEntity someMenuEntity1() {
        return MenuEntity.builder()
            .menuId(1)
            .menuName("some menu name")
            .description("some description")
            .foods(FoodUtils.someSetOfFoodEntities())
            .build();
    }

    public static MenuEntity someMenuEntity2() {
        return MenuEntity.builder()
            .menuName("another menu name")
            .menuId(123)
            .foods(Set.of(FoodUtils.someFoodEntity1()))
            .build();
    }

    public static MenuEntity someMenuEntity3() {
        return MenuEntity.builder()
            .menuName("and another menu name")
            .menuId(4)
            .foods(Set.of(FoodUtils.someFoodEntity2()))
            .build();
    }

    public static MenuEntity someMenuEntity4() {
        return MenuEntity.builder()
            .menuName("and another menu name")
            .menuId(4)
            .build();
    }

    public static MenuEntity someMenuToPersist1() {
        return MenuEntity.builder()
            .menuName("Menu to Persist 1")
            .build();
    }

    public static MenuEntity someMenuToPersist2() {
        return MenuEntity.builder()
            .menuName("Menu to Persist 2")
            .build();
    }
}
