package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.domain.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalMapper {


    RestaurantDTO mapToDTO(Restaurant restaurant);

    Restaurant mapFromDTO(RestaurantDTO restaurantDTO);
}
