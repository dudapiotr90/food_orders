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
public class OrderDTO {
    private Integer orderId;
    private String orderNumber;
    private String receivedDateTime;
    private String completedDateTime;
    private String customerComment;
    private Boolean realized;
//    private Set<OrderRequestDTO> orderDetails;
    private List<OrderItemDTO> orderItems;
    private RestaurantDTO restaurant;

}
