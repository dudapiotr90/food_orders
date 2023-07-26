package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.FoodImage;
import com.dudis.foodorders.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodDAO {
    void saveFood(Food food, Menu menu, String foodImagePath);

    void updateFood(Food foodId);

    void deleteFood(Integer foodId);

    Page<Food> getPaginatedFoods(Integer menuId, Pageable pageable);
}
