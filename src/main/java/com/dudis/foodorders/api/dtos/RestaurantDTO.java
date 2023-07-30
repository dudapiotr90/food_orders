package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.LocalType;
import lombok.*;

import java.util.Set;
@With
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
    private Integer deliveriesSize;
    private Integer deliveryAddressesSize;
    private Set<OrderDTO> orders;


}
