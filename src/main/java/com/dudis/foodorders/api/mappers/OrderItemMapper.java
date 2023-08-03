package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = FoodMapper.class)
public interface OrderItemMapper {

    @Mapping(target = "cart",ignore = true)
    @Mapping(target = "order",ignore = true)
    @Mapping(target = "totalCost",ignore = true)
    @Mapping(source = "food",target = "food",qualifiedByName = "mapFoodToDTO")
    OrderItemDTO mapToDTO(OrderItem orderItem);

    @Mapping(target = "cart",ignore = true)
    @Mapping(target = "order",ignore = true)
    OrderItem mapFromDTO(OrderItemDTO orderItemDTO);
}
