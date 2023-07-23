package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressDAO {
    List<DeliveryAddress> getRestaurantDeliveryAddresses(Integer restaurantId);
}
