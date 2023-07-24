package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MenuEntityMapper {

    @Mapping(target="foods",source = "foods",qualifiedByName = "mapFoods")
    Menu mapFromEntity(MenuEntity menu);

    @Named("mapFoods")
    default Set<Food> mapFoods(Set<FoodEntity> foods) {
        return foods.stream().map(this::mapFoodFromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "menu",ignore = true)
    Food mapFoodFromEntity(FoodEntity foodEntity);

    @Mapping(target = "menu",ignore = true)
    FoodEntity mapFoodToEntity(Food food);

    @Mapping(target = "foods",ignore = true)
    MenuEntity mapToEntity(Menu menu);
}
