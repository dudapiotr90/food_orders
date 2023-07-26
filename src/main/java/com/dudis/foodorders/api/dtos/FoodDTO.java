package com.dudis.foodorders.api.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Base64;

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

}
