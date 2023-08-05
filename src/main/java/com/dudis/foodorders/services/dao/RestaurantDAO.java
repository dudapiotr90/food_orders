package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.mappers.ApiRoleEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RestaurantDAO {
    List<Restaurant> findRestaurantsWhereOwnerId(Integer accountId);

    void addLocal(Restaurant restaurant);

    Optional<Restaurant> findProcessingRestaurant(Integer restaurantId);

    Optional<Menu> getMenu(Integer restaurantId);

    Page<Menu> getPaginatedMenu(Integer restaurantId, Pageable pageable);

    Restaurant findRestaurantByMenu(Menu menu);

    Optional<Owner> findOwnerByRestaurant(Restaurant restaurant);
}
