package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = FoodMapper.class)
public interface MenuMapper {
    @Mapping(target = "foods", source = "foods", qualifiedByName = "mapFoods")
    @Mapping(target = "menuDescription", source = "description")
    MenuDTO mapToDTO(Menu menu);

}
