package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.FoodRequestDTO;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {


    default OrderItem mapFoodRequestToOrderItem(FoodRequestDTO foodToAdd) {
        return OrderItem.builder()
            .quantity(foodToAdd.getQuantity())
            .food(Food.builder()
                .foodId(foodToAdd.getFoodId())
                .build())
            .build();
    }
}
