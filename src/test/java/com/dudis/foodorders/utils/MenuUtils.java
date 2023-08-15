package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.domain.Menu;

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
            .menuName("some menu name")
            .menuId(1)
            .foods(FoodUtils.someFoodsSet())
            .build();
    }

    public static Menu someMenu2() {
        return Menu.builder()
            .menuName("another menu name")
            .menuId(123)
            .foods(Set.of())
            .build();
    }

    public static Menu someMenu3() {
        return Menu.builder()
            .menuName("and another menu name")
            .menuId(4)
            .foods(Set.of())
            .build();
    }
}
