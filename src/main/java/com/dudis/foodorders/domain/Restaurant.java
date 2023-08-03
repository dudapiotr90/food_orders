package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.awt.*;
import java.util.Set;

@With
@Value
@Builder
public class Restaurant {

    Integer restaurantId;
    String name;
    String description;
    LocalType type;
    Menu menu;
    Owner owner;
    Set<DeliveryAddress> deliveryAddresses;
    Set<Order> orders;

}
