package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = {OrderEntityMapper.class})
public interface RestaurantEntityMapper {
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "deliveryAddresses", ignore = true)
    @Mapping(source = "orders", target = "orders", qualifiedByName = "mapOrderEntities")
    @Mapping(source = "menu.foods", target = "menu.foods", ignore = true)
    Restaurant mapFromEntity(RestaurantEntity local);

    RestaurantEntity mapToEntity(Restaurant restaurant);
}
