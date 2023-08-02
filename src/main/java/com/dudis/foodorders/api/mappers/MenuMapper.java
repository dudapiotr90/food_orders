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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = FoodMapper.class)
public interface MenuMapper {
    @Mapping(target = "foods", source = "foods", qualifiedByName = "mapFoods")
    MenuDTO mapToDTO(Menu menu);


    @Named("mapMenuToMenuId")
    default Integer mapMenuToMenuId(Menu menu) {
        return menu.getMenuId();
    }
}
