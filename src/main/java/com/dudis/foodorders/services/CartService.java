package com.dudis.foodorders.services;

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
    public void addItemToCart(Cart cart, OrderItem itemToAdd) {
        orderItemService.addItemToCart(cart, itemToAdd);
    }
}
