package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.RestaurantJpaRepository;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    @Override
    public List<Restaurant> findLocalsWhereOwnerId(Integer ownerId) {
        return restaurantJpaRepository.findByOwnerId(ownerId).stream()
            .map(restaurantEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public void addLocal(Restaurant restaurant) {
        restaurantJpaRepository.save(restaurantEntityMapper.mapToEntity(restaurant));
    }
}
