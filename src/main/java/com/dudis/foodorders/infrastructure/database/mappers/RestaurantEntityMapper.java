package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantEntityMapper {
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "deliveries", ignore = true)
    @Mapping(target = "deliveryAddresses", ignore = true)
    @Mapping(target = "orders", ignore = true)
//    @Mapping(target = "menu", ignore = true)
    Restaurant mapFromEntity(RestaurantEntity local);

//    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "menu", ignore = true)
    RestaurantEntity mapToEntity(Restaurant restaurant);
}
