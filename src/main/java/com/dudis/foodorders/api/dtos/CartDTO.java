package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    Integer cartId;
    Customer customer;
    Set<OrderRequestDTO> orderDetails;
    Set<OrderItemDTO> orderItems;
}
