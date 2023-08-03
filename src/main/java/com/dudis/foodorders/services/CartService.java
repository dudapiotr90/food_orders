package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.services.dao.CartDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartService {

    private final CartDAO cartDAO;
    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;
    public void addItemToCart(Cart cart, OrderItem itemToAdd) {
        orderItemService.addItemToCart(cart, itemToAdd);
    }

    public void updateOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemMapper.mapFromDTO(orderItemDTO);
        orderItemService.updateOrderItem(orderItem);
    }
}
