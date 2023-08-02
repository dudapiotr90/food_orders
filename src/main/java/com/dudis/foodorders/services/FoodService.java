package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.mappers.FoodMapper;
import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.services.dao.FoodDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodDAO foodDAO;
    private final FoodMapper foodMapper;


    public void addFoodToMenu(Food food, Menu menu, String foodImagePath) {
        foodDAO.saveFood(food, menu,foodImagePath);
    }

    public String updateMenuPosition(Food food, String foodImagePath) {
        return foodDAO.updateFood(food,foodImagePath);
    }

    public String deleteFood(Integer foodId) {
        return foodDAO.deleteFood(foodId);
    }

    public Page<FoodDTO> getPaginatedFoods(Integer menuId, Pageable pageable) {
        return foodDAO.getPaginatedFoods(menuId,pageable).map(foodMapper::mapToDTO);
    }

    public Menu findMenuByFood(Food food) {
        return foodDAO.findMenuByFood(food);
    }

    public List<Food> findAllFoodWhereMenu(Menu menu) {
        return foodDAO.findAllFoodWhereMenu(menu);
    }
}
