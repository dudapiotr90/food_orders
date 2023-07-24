package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {
    @Mapping(target = "foods", source = "foods", qualifiedByName = "mapFoods")
    MenuDTO mapToDTO(Menu menu);

    @Named("mapFoods")
    default Set<FoodDTO> mapFoods(Set<Food> foods) {
        return foods.stream().map(this::mapFoodToDTO).collect(Collectors.toSet());
    }

    FoodDTO mapFoodToDTO(Food food);
    Food mapFoodFromDTO(FoodDTO food);
}
