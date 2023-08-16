package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.LocalType;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = {OrderEntityMapper.class})
public interface RestaurantEntityMapper {
    @Named("mapRestaurantFromEntity")
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "deliveryAddresses", ignore = true)
    @Mapping(source = "orders", target = "orders", qualifiedByName = "mapOrdersFromEntity")
    @Mapping(source = "menu.foods", target = "menu.foods", ignore = true)
    Restaurant mapFromEntity(RestaurantEntity local);

    @Mapping(source = "orders" ,target = "orders",qualifiedByName = "mapOrdersToEntity")
    RestaurantEntity mapToEntity(Restaurant restaurant);

    default Restaurant buildRestaurantFromObject(Object[] o) {
        return Restaurant.builder()
            .restaurantId((Integer) o[0])
            .name((String) o[1])
            .description((String) o[2])
            .type(LocalType.valueOf((String) o[3]))
            .build();
    }
}
