package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.services.dao.FoodDAO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FoodService {
    private final FoodDAO foodDAO;

    private final MenuMapper menuMapper;

    public void addFoodToMenu(FoodDTO foodDTO, Menu menu) {
        foodDAO.saveFood(menuMapper.mapFoodFromDTO(foodDTO), menu);
    }

    @Transactional
    public void updateMenuPosition(FoodDTO foodDTO) {
        Food foodToUpdate = menuMapper.mapFoodFromDTO(foodDTO);
        foodDAO.updateFood(foodToUpdate);
    }

    public void deleteFood(Integer foodId) {
        foodDAO.deleteFood(foodId);
    }

    public Page<FoodDTO> getPaginatedFoods(Integer menuId, Pageable pageable) {
        return foodDAO.getPaginatedFoods(menuId,pageable).map(menuMapper::mapFoodToDTO);
    }
}
