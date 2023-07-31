package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.LocalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantForCustomerDTO {
    private Integer restaurantId;
    private String name;
    private String description;
    private LocalType type;
}
