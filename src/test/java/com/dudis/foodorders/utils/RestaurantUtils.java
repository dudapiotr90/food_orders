package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.dtos.RestaurantForCustomerDTO;
import com.dudis.foodorders.domain.LocalType;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;

import java.util.List;
import java.util.Set;

public class RestaurantUtils {

    public static List<Restaurant> someRestaurants() {
        return List.of(someRestaurant1(),someRestaurant2(),someRestaurant3());
    }

    public static List<RestaurantEntity> someRestaurantEntities() {
        return List.of(someRestaurantEntity1(),someRestaurantEntity2(),someRestaurantEntity3());
    }
    public static List<RestaurantDTO> someRestaurantsDTO() {
        return List.of(someRestaurantDTO1(),someRestaurantDTO2(),someRestaurantDTO3());
    }
    public static List<RestaurantForCustomerDTO> someRestaurantsForCustomerDTO() {
        return List.of(someRestaurantForCustomerDTO1(),someRestaurantForCustomerDTO2(),someRestaurantForCustomerDTO3());
    }

    public static Restaurant someRestaurant1() {
        return Restaurant.builder()
            .restaurantId(1)
            .name("Restaurant Name 1")
            .type(LocalType.BAKERY)
            .menu(MenuUtils.someMenu3())
            .deliveryAddresses(Set.of(DeliveryAddressUtils.someDeliveryAddress1(),DeliveryAddressUtils.someDeliveryAddress2()))
            .build();
    }

    public static Restaurant someRestaurant2() {
        return Restaurant.builder()
            .restaurantId(2)
            .name("Restaurant Name 2")
            .menu(MenuUtils.someMenu1())
            .build();
    }

    public static Restaurant someRestaurant3() {
        return Restaurant.builder()
            .restaurantId(3)
            .name("Restaurant Name 3")
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



    public static RestaurantEntity someRestaurantEntity1() {
        return RestaurantEntity.builder()
            .restaurantId(3)
            .name("RestaurantEntity Name 3")
            .menu(MenuUtils.someMenuEntity3())
            .build();
    }

    public static RestaurantEntity someRestaurantEntity2() {
        return RestaurantEntity.builder()
            .restaurantId(2)
            .name("RestaurantEntity Name 2")
            .menu(MenuUtils.someMenuEntity1())
            .build();
    }

    public static RestaurantEntity someRestaurantEntity3() {
        return RestaurantEntity.builder()
            .restaurantId(1)
            .name("RestaurantEntity Name 1")
            .menu(MenuUtils.someMenuEntity2())
            .build();
    }

    public static List<Object[]> someRestaurantsAsObjects() {
        return List.of(someRestaurantAsObject1(),someRestaurantAsObject2(),someRestaurantAsObject3());
    }

    public static Object[] someRestaurantAsObject1() {
        return new Object[]{"1","Restaurant Name 1","some description 1","BAKERY"};
    }
    private static Object[] someRestaurantAsObject2() {
        return new Object[]{"2","Restaurant Name 2","some description 2","DRINK_SHOP"};
    }
    public static Object[] someRestaurantAsObject3() {
        return new Object[]{"3","RestaurantDTO Name 3","some description 3","COFFEE_HOUSE"};
    }
}
