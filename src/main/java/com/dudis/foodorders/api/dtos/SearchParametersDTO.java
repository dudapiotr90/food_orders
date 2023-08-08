package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchParametersDTO {
    private String query;
    private String[] cuisine;
    private String diet;
    private String mealType;
    private BigDecimal maxVideoLengthInSeconds;
}
