package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.FoodTypeEntity;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MenuEntityMapper {

    @Mapping(target = "restaurant",ignore = true)
    @Mapping(target="foods",source = "foods",qualifiedByName = "mapFoods")
    Menu mapFromEntity(MenuEntity menu);

    @Named("mapFoods")
    default Set<Food> mapFoods(Set<FoodEntity> foods) {
        return foods.stream().map(this::mapFood).collect(Collectors.toSet());
    }

    @Mapping(target = "menu",ignore = true)
    @Mapping(target = "foodType",ignore = true)
    Food mapFood(FoodEntity foodEntity);
}
