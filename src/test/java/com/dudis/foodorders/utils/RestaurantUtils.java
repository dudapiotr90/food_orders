package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.dtos.RestaurantForCustomerDTO;
import com.dudis.foodorders.domain.Restaurant;

import java.util.List;

public class RestaurantUtils {

    public static List<Restaurant> someRestaurants() {
        return List.of(someRestaurant1(),someRestaurant2(),someRestaurant3());
    }

    public static List<RestaurantDTO> someRestaurantsDTO() {
        return List.of(someRestaurantDTO1(),someRestaurantDTO2(),someRestaurantDTO3());
    }

    public static Restaurant someRestaurant1() {
        return Restaurant.builder()
            .restaurantId(3)
            .name("Restaurant Name 3")
            .menu(MenuUtils.someMenu3())
            .build();
    }

    public static Restaurant someRestaurant2() {
        return Restaurant.builder()
            .restaurantId(2)
            .name("Restaurant Name 2")
            .menu(MenuUtils.someMenu())
            .build();
    }

    public static Restaurant someRestaurant3() {
        return Restaurant.builder()
            .restaurantId(1)
            .name("Restaurant Name 1")
            .menu(MenuUtils.someMenu2())
            .build();
    }

    public static RestaurantForCustomerDTO someRestaurantForCustomerDTO1() {
        return RestaurantForCustomerDTO.builder()
            .restaurantId(1)
            .name("Restaurant Name 1")
            .build();
    }

    public static RestaurantForCustomerDTO someRestaurantForCustomerDTO2() {
        return RestaurantForCustomerDTO.builder()
            .restaurantId(2)
            .name("Restaurant Name 2")
            .build();
    }

    public static RestaurantForCustomerDTO someRestaurantForCustomerDTO3() {
        return RestaurantForCustomerDTO.builder()
            .restaurantId(3)
            .name("Restaurant Name 3")
            .build();
    }

    public static RestaurantDTO someRestaurantDTO1() {
        return RestaurantDTO.builder()
            .restaurantId(3)
            .name("RestaurantDTO Name 3")
            .build();
    }

    public static RestaurantDTO someRestaurantDTO2() {
        return RestaurantDTO.builder()
            .restaurantId(2)
            .name("RestaurantDTO Name 2")
            .build();
    }

    public static RestaurantDTO someRestaurantDTO3() {
        return RestaurantDTO.builder()
            .restaurantId(1)
            .name("RestaurantDTO Name 1")
            .build();
    }

}
