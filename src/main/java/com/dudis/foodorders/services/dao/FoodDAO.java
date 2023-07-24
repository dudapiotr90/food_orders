package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FoodDAO {
    void saveFood(Food food, Menu menu);

    void updateFood(Food foodId);

    void deleteFood(Integer foodId);

    Page<Food> getPaginatedFoods(Integer menuId, Pageable pageable);
}
