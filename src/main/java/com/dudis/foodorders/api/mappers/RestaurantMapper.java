package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    @Mapping(target = "menuDTO",source = "menu",ignore = true)
    RestaurantDTO mapToDTO(Restaurant restaurant);

//    @Mapping(source = "menuDTO.menuName",target ="menu.menuName")
//    @Mapping(source = "menuDTO.menuDescription",target ="menu.menuName")
    @Mapping(target ="owner",ignore = true)
    @Mapping(target ="deliveries",ignore = true)
    @Mapping(target ="deliveryAddresses",ignore = true)
    @Mapping(target ="orders",ignore = true)
    Restaurant mapFromDTO(RestaurantDTO restaurantDTO);

}
