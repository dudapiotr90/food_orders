package com.dudis.foodorders.api.dtos;

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
public class MenuDTO {

    private String menuName;
    private String menuDescription;
    Set<FoodDTO> foods;

    public Map<String, Object> asMap() {
        Map<String, Object> result = new HashMap<>();
        Optional.ofNullable(menuName).ifPresent(value -> result.put("menuName", value));
        Optional.ofNullable(menuDescription).ifPresent(value -> result.put("menuDescription", value));
        Optional.ofNullable(foods).ifPresent(value -> result.put("foods", ""));
        return result;
    }
}
