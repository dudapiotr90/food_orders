package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.dtos.RestaurantForCustomerDTO;
import com.dudis.foodorders.domain.Restaurant;

import java.util.List;

public class RestaurantUtils {
    public static RestaurantDTO someRestaurantDTO() {
        return RestaurantDTO.builder()
            .name("Restaurant Name")
            .description("Some restaurant description")
            .build();
    }

    public static List<Restaurant> someRestaurants() {
        return List.of(someRestaurant1(),someRestaurant2(),someRestaurant3());
    }

    public static Restaurant someRestaurant3() {
        return Restaurant.builder()
            .restaurantId(1)
            .name("Restaurant Name 1")
            .build();
    }

    public static Restaurant someRestaurant2() {
        return Restaurant.builder()
            .restaurantId(2)
            .name("Restaurant Name 2")
            .build();
    }

    public static Restaurant someRestaurant1() {
        return Restaurant.builder()
            .restaurantId(3)
            .name("Restaurant Name 3")
            .build();
    }

    public static RestaurantForCustomerDTO someRestaurantDTO1() {
        return RestaurantForCustomerDTO.builder()
            .restaurantId(1)
            .name("Restaurant Name 1")
            .build();
    }

    public static RestaurantForCustomerDTO someRestaurantDTO2() {
        return RestaurantForCustomerDTO.builder()
            .restaurantId(2)
            .name("Restaurant Name 2")
            .build();
    }

    public static RestaurantForCustomerDTO someRestaurantDTO3() {
        return RestaurantForCustomerDTO.builder()
            .restaurantId(3)
            .name("Restaurant Name 3")
            .build();
    }
}
