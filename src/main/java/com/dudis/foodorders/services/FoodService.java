package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.services.dao.FoodDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodDAO foodDAO;

    private final MenuMapper menuMapper;

    public void addFoodToMenu(Food food, Menu menu, String foodImagePath) {
        foodDAO.saveFood(food, menu,foodImagePath);
    }

    public String updateMenuPosition(Food food, String foodImagePath) {
        return foodDAO.updateFood(food,foodImagePath);
    }

    public void deleteFood(Integer foodId) {
        foodDAO.deleteFood(foodId);
    }

    public Page<FoodDTO> getPaginatedFoods(Integer menuId, Pageable pageable) {
        return foodDAO.getPaginatedFoods(menuId,pageable).map(menuMapper::mapFoodToDTO);
    }
}
