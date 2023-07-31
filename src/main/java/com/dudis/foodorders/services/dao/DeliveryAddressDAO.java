package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliveryAddressDAO {
    List<DeliveryAddress> getRestaurantDeliveryAddresses(Integer restaurantId);

    void addDeliveryAddressToRestaurant(DeliveryAddress deliveryAddress, Restaurant restaurant);

    Page<DeliveryAddress> getPaginatedRestaurantDeliveryAddresses(Integer restaurantId, Pageable pageable);

    Integer countDeliveryAddressesForRestaurant(Restaurant restaurant);

    void deleteById(Integer deliveryId);

    List<Restaurant> findRestaurantsIdWithAddress(String city, String postalCode, String address);
}
