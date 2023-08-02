package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.FoodJpaRepository;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.MenuJpaRepository;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.RestaurantJpaRepository;
import com.dudis.foodorders.services.dao.MenuDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MenuRepository implements MenuDAO {

    private final MenuJpaRepository menuJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final MenuEntityMapper menuEntityMapper;

}
