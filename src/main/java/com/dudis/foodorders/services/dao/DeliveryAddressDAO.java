package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Restaurant;

import java.util.List;

public interface DeliveryAddressDAO {
    List<DeliveryAddress> getRestaurantDeliveryAddresses(Integer restaurantId);

    void addDeliveryAddressToRestaurant(DeliveryAddress deliveryAddress, Restaurant restaurant);
}
