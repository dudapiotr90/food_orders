package com.dudis.foodorders.api.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {

    private Integer foodId;
    private String name;
    private String description;
    private BigDecimal price;
    private String foodType;
    private String foodImageAsBase64;
    private String foodImagePath;

    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        Optional.ofNullable(foodId).ifPresent(value -> result.put("foodId", value.toString()));
        Optional.ofNullable(name).ifPresent(value -> result.put("name", value));
        Optional.ofNullable(description).ifPresent(value -> result.put("description", value));
        Optional.ofNullable(price).ifPresent(value -> result.put("price", value.toString()));
        Optional.ofNullable(foodType).ifPresent(value -> result.put("foodType", value));
        Optional.ofNullable(foodImageAsBase64).ifPresent(value -> result.put("foodImageAsBase64", value));
        Optional.ofNullable(foodImagePath).ifPresent(value -> result.put("foodImagePath", value));
        return result;
    }

}
