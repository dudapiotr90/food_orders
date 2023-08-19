package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.dtos.RestaurantForCustomerDTO;
import com.dudis.foodorders.domain.LocalType;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;

import java.util.List;
import java.util.Set;

import static com.dudis.foodorders.utils.DeliveryAddressUtils.*;
import static com.dudis.foodorders.utils.OrderUtils.someOrderEntity1;
import static com.dudis.foodorders.utils.OrderUtils.someOrderEntity2;

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
            .deliveryAddresses(Set.of(someDeliveryAddress1(), someDeliveryAddress2()))
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
    public static Restaurant someRestaurant4() {
        return Restaurant.builder()
            .restaurantId(3)
            .name("Restaurant Name 3")
            .menu(MenuUtils.someMenu2())
            .orders(Set.of(OrderUtils.someOrder3()))
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
    public static RestaurantDTO someRestaurantDTO4() {
        return RestaurantDTO.builder()
            .restaurantId(1)
            .name("RestaurantDTO Name 1")
            .menuDTO(MenuUtils.someMenuDTO())
            .type(LocalType.BAKERY)
            .description("Some DTO description")
            .build();
    }



    public static RestaurantEntity someRestaurantEntity1() {
        return RestaurantEntity.builder()
            .restaurantId(3)
            .name("RestaurantEntity Name 3")
            .menu(MenuUtils.someMenuEntity3())
            .orders(Set.of(someOrderEntity1()))
            .build();
    }

    public static RestaurantEntity someRestaurantEntity2() {
        return RestaurantEntity.builder()
            .restaurantId(2)
            .name("RestaurantEntity Name 2")
            .type(LocalType.DRINK_SHOP)
            .menu(MenuUtils.someMenuEntity1())
            .owner(OwnerUtils.someOwnerEntity2())
            .orders(Set.of(someOrderEntity1(),someOrderEntity2()))
            .deliveryAddresses(Set.of(someDeliveryAddressEntity1(),someDeliveryAddressEntity2(),someDeliveryAddressEntity3()))
            .build();
    }

    public static RestaurantEntity someRestaurantEntity3() {
        return RestaurantEntity.builder()
            .restaurantId(1)
            .name("RestaurantEntity Name 1")
            .menu(MenuUtils.someMenuEntity2())
//            .orders(Set.of(someOrderEntity1(),someOrderEntity2()))
            .build();
    }

    public static List<Object[]> someRestaurantsAsObjects() {
        return List.of(someRestaurantAsObject1(),someRestaurantAsObject2(),someRestaurantAsObject3());
    }

    public static Restaurant someRestaurantFromObject() {
        return Restaurant.builder()
            .restaurantId(1)
            .name("Restaurant Name 1")
            .description("some description 1")
            .type(LocalType.BAKERY)
            .build();
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

    public static RestaurantEntity someRestaurantToPersist1() {
        return RestaurantEntity.builder()
            .name("RestaurantEntity Name 1")
            .type(LocalType.RESTAURANT)
            .menu(MenuUtils.someMenuToPersist1())
            .build();
    }
    public static RestaurantEntity someRestaurantToPersist2() {
        return RestaurantEntity.builder()
            .name("RestaurantEntity Name 2")
            .type(LocalType.BAKERY)
            .menu(MenuUtils.someMenuToPersist2())
            .build();
    }

}
