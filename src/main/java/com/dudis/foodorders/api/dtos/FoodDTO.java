package com.dudis.foodorders.api.dtos;


import com.dudis.foodorders.domain.FoodType;
import com.dudis.foodorders.domain.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {

    Integer foodId;
    String name;
    String description;
    BigDecimal price;
    FoodTypeDTO foodType;
}
