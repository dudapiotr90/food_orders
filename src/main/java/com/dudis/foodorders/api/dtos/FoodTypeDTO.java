package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodTypeDTO {

    private Integer foodTypeId;
    private String name;
    private Set<Food> foods;
}
