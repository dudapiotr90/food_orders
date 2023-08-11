package com.dudis.foodorders.services;

import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.services.dao.MenuDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MenuService{
    private final FoodService foodService;

    public boolean menuContainsFood(Food food, Menu menu) {
        List<Food> foods = foodService.findAllFoodWhereMenu(menu);
        return foods.stream()
            .anyMatch(f-> Objects.equals(f.getFoodId(), food.getFoodId()));
    }
}
