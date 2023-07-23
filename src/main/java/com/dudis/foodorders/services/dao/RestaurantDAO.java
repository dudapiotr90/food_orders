package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantDAO {
    List<Restaurant> findRestaurantsWhereOwnerId(Integer accountId);

    void addLocal(Restaurant restaurant);

    Optional<Restaurant> findProcessingRestaurant(Integer restaurantId);

    Optional<Menu> getMenu(Integer restaurantId);

    List<DeliveryAddress> getDeliveryAddresses(Integer restaurantId);
}
