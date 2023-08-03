package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {

    private RestaurantForCustomerDTO restaurant;
    private List<OrderItemDTO> orderItems;
    private String customerComment;
}
