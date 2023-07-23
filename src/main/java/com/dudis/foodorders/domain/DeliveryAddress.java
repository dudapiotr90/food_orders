package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class DeliveryAddress {

    Integer deliveryAddressId;
    String city;
    String postalCode;
    String street;
    Restaurant restaurant;
}
