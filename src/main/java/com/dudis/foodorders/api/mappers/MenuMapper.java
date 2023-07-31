package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.FoodImage;
import com.dudis.foodorders.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Objects;
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
    @Mapping(source = "foodImagePath",target = "foodImageAsBase64",qualifiedByName = "mapFoodImage")
    @Mapping(source = "foodImagePath",target = "foodImagePath")
    FoodDTO mapFoodToDTO(Food food);

    @Mapping(target = "foodImagePath",ignore = true)
    Food mapFoodFromDTO(FoodDTO food);

    @Named("mapFoodImage")
    default String mapFoodImage(String foodImagePath) throws IOException {
        if (Objects.isNull(foodImagePath)) {
            return null;
        }
        byte[] imageAsBytes = Files.readAllBytes(new File(foodImagePath).toPath());
        return Base64.getEncoder().encodeToString(imageAsBytes);
    }

    @Named("mapMenuToMenuId")
    default Integer mapMenuToMenuId(Menu menu) {
        return menu.getMenuId();
    }
}
