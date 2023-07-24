package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.RestaurantJpaRepository;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final MenuEntityMapper menuEntityMapper;
    @Override
    public List<Restaurant> findRestaurantsWhereOwnerId(Integer ownerId) {
        return restaurantJpaRepository.findByOwnerId(ownerId).stream()
            .map(restaurantEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public void addLocal(Restaurant restaurant) {
        restaurantJpaRepository.save(restaurantEntityMapper.mapToEntity(restaurant));
    }

    @Override
    public Optional<Restaurant> findProcessingRestaurant(Integer restaurantId) {
        return restaurantJpaRepository.findById(restaurantId)
            .map(restaurantEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Menu> getMenu(Integer restaurantId) {
        return restaurantJpaRepository.findMenuByRestaurantId(restaurantId)
            .map(menuEntityMapper::mapFromEntity);
    }
}
