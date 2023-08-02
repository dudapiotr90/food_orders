package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodEntityMapper {


    @Mapping(target = "menu",ignore = true)
    Food mapFromEntity(FoodEntity foodEntity);

    @Mapping(target = "menu",ignore = true)
    FoodEntity mapToEntity(Food food);

    @Named("mapFoods")
    default Set<Food> mapFoods(Set<FoodEntity> foods) {
        return foods.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }
}
