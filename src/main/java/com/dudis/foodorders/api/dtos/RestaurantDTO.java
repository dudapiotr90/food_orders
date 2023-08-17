package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.LocalType;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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


    public Map<String, Object> asMap() {
        Map<String, Object> result = new HashMap<>();
        Optional.ofNullable(restaurantId).ifPresent(value -> result.put("restaurantId", value));
        Optional.ofNullable(name).ifPresent(value -> result.put("name", value));
        Optional.ofNullable(description).ifPresent(value -> result.put("description", value));
        Optional.ofNullable(type).ifPresent(value -> result.put("type", value));
        Optional.ofNullable(menuDTO).ifPresent(value -> result.put("menuDTO", value.asMap()));
        Optional.ofNullable(deliveriesSize).ifPresent(value -> result.put("deliveriesSize", value));
        Optional.ofNullable(deliveryAddressesSize).ifPresent(value -> result.put("deliveryAddressesSize", value));
        Optional.ofNullable(orders).ifPresent(value -> result.put("orders", "value"));
        return result;
    }
}
