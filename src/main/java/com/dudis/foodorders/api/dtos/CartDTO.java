package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.domain.OrderDetail;
import com.dudis.foodorders.domain.OrderItem;
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
public class CartDTO {
    Integer cartId;
    Customer customer;
    Set<OrderDetailDTO> orderDetails;
    Set<OrderItemDTO> orderItems;
}
