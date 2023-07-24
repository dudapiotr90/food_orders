package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.services.dao.FoodDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodDAO foodDAO;

    public void addFoodToMenu(Food food, Menu menu) {
        foodDAO.saveFood(food, menu);
    }
}
