package com.dudis.foodorders.utils;

import com.dudis.foodorders.domain.Menu;

public class MenuUtils {
    public static Menu someMenu() {
        return Menu.builder()
            .menuName("some menu name")
            .menuId(1)
            .foods(FoodUtils.someFoods())
            .build();
    }
}
