package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "menuDTO",source = "menu",qualifiedByName = "mapMenu")
    RestaurantDTO mapToDTO(Restaurant restaurant);



}
