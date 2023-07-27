package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.FoodJpaRepository;
import com.dudis.foodorders.services.dao.FoodDAO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class FoodRepository implements FoodDAO {

    private final FoodJpaRepository foodJpaRepository;
    private final MenuEntityMapper menuEntityMapper;

    @Override
    public void saveFood(Food food, Menu menu, String foodImagePath) {
        MenuEntity menuEntity = menuEntityMapper.mapToEntity(menu);
        FoodEntity foodToSave = menuEntityMapper.mapFoodToEntity(food);
        foodToSave.setMenu(menuEntity);
        foodToSave.setFoodImagePath(foodImagePath);
        foodJpaRepository.saveAndFlush(foodToSave);
    }

    @Override
    public String updateFood(Food food, String foodImagePath) {
        FoodEntity existingFood = foodJpaRepository.findById(food.getFoodId())
            .orElseThrow(() -> new EntityNotFoundException("FoodEntity with id: [%s] not found".formatted(food.getFoodId())));
        String imageToDelete = existingFood.getFoodImagePath();
        existingFood.setName(food.getName());
        existingFood.setDescription(food.getDescription());
        existingFood.setFoodType(food.getFoodType());
        existingFood.setPrice(food.getPrice());
        existingFood.setFoodImagePath(foodImagePath);
        FoodEntity updatedFood = foodJpaRepository.saveAndFlush(existingFood);
        return imageToDelete;
    }

    @Override
    public String deleteFood(Integer foodId) {
        FoodEntity existingFood = foodJpaRepository.findById(foodId)
            .orElseThrow(() -> new EntityNotFoundException("FoodEntity with id: [%s] not found".formatted(foodId)));
        foodJpaRepository.deleteById(foodId);
        return existingFood.getFoodImagePath();
    }

    @Override
    public Page<Food> getPaginatedFoods(Integer menuId, Pageable pageable) {
        return foodJpaRepository.findByMenuId(menuId, pageable).map(menuEntityMapper::mapFoodFromEntity);
    }

}
