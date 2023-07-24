package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;

import java.util.Optional;

public interface FoodDAO {
    void saveFood(Food food, Menu menu);

    void updateFood(Food foodId);
}
