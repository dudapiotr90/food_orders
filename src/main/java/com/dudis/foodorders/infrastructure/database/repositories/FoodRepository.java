package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.infrastructure.database.mappers.FoodEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.FoodJpaRepository;
import com.dudis.foodorders.services.dao.FoodDAO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class FoodRepository implements FoodDAO {

    private final FoodJpaRepository foodJpaRepository;
    private final FoodEntityMapper foodEntityMapper;
    private final MenuEntityMapper menuEntityMapper;

    @Override
    public void saveFood(Food food, Menu menu, String foodImagePath) {
        MenuEntity menuEntity = menuEntityMapper.mapToEntity(menu);
        FoodEntity foodToSave = foodEntityMapper.mapToEntity(food);
        foodToSave.setMenu(menuEntity);
        foodToSave.setFoodImagePath(foodImagePath);
        foodJpaRepository.saveAndFlush(foodToSave);
    }

    @Override
    public String updateFood(Food food, String foodImagePath) {
        FoodEntity existingFood = getExistingFood(food.getFoodId());
        String imageToDelete = existingFood.getFoodImagePath();
        existingFood.setName(food.getName());
        existingFood.setDescription(food.getDescription());
        existingFood.setFoodType(food.getFoodType());
        existingFood.setPrice(food.getPrice());
        existingFood.setFoodImagePath(foodImagePath);
        foodJpaRepository.saveAndFlush(existingFood);
        return imageToDelete;
    }

    @Override
    public String deleteFood(Integer foodId) {
        FoodEntity existingFood = getExistingFood(foodId);
        foodJpaRepository.deleteById(foodId);
        return existingFood.getFoodImagePath();
    }

    @Override
    public Page<Food> getPaginatedFoods(Integer menuId, Pageable pageable) {
        return foodJpaRepository.findByMenuId(menuId, pageable).map(foodEntityMapper::mapFromEntity);
    }

    @Override
    public Menu findMenuByFood(Food food) {
        FoodEntity foodEntity = foodEntityMapper.mapToEntity(food);
        MenuEntity menu = foodJpaRepository.findMenuByFoodId(foodEntity.getFoodId());
        return menuEntityMapper.mapFromEntity(menu);
    }

    @Override
    public List<Food> findAllFoodWhereMenu(Menu menu) {
        MenuEntity menuEntity = menuEntityMapper.mapToEntity(menu);
        return foodJpaRepository.findByMenuId(menuEntity.getMenuId()).stream()
            .map(foodEntityMapper::mapFromEntity).toList();
    }

    private FoodEntity getExistingFood(Integer foodId) {
        return foodJpaRepository.findById(foodId)
            .orElseThrow(() -> new EntityNotFoundException("FoodEntity with id: [%s] not found".formatted(foodId)));
    }

}
