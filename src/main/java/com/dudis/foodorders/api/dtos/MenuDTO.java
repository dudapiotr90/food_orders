package com.dudis.foodorders.api.dtos;

import lombok.*;

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

}
