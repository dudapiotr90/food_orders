package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Restaurant;

import java.util.List;

public interface RestaurantDAO {
    List<Restaurant> findLocalsWhereOwnerId(Integer accountId);

    void addLocal(Restaurant restaurant);
}
