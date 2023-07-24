package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;

public interface FoodDAO {
    void saveFood(Food food, Menu menu);
}
