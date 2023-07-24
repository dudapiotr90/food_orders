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
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FoodRepository implements FoodDAO {

    private final FoodJpaRepository foodJpaRepository;
    private final MenuEntityMapper menuEntityMapper;
    @Override
    public void saveFood(Food food, Menu menu) {
        MenuEntity menuEntity = menuEntityMapper.mapToEntity(menu);
        FoodEntity foodToSave = menuEntityMapper.mapFoodToEntity(food);
        foodToSave.setMenu(menuEntity);
        foodJpaRepository.saveAndFlush(foodToSave);
    }

    @Override
    public void updateFood(Food food) {
       FoodEntity existingFood = foodJpaRepository.findById(food.getFoodId())
           .orElseThrow(() -> new EntityNotFoundException("FoodEntity with id: [%s] not found".formatted(food.getFoodId())));
       existingFood.setName(food.getName());
       existingFood.setDescription(food.getDescription());
       existingFood.setFoodType(food.getFoodType());
       existingFood.setPrice(food.getPrice());
        foodJpaRepository.save(existingFood);
    }

}
