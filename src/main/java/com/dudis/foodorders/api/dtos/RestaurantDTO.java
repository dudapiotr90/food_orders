package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.LocalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    private Integer restaurantId;
    private String name;
    private String description;
    private LocalType type;
    private MenuDTO menuDTO;
    private Set<OrderDTO> orders;


}
