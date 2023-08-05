package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Integer restaurantId;
    private String customerComment;
    private Set<Integer> orderItemsId;
}
