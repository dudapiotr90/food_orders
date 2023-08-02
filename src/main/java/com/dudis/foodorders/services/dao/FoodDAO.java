package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.FoodImage;
import com.dudis.foodorders.domain.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodDAO {
    void saveFood(Food food, Menu menu, String foodImagePath);

    String updateFood(Food food,String foodImagePath);

    String deleteFood(Integer foodId);

    Page<Food> getPaginatedFoods(Integer menuId, Pageable pageable);

    Menu findMenuByFood(Food food);

    List<Food> findAllFoodWhereMenu(Menu menu);
}
