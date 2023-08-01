package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.services.dao.OrderItemDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemDAO orderItemDAO;
    public void addItemToCart(Cart cart, OrderItem itemToAdd) {
        orderItemDAO.addItemToCart(cart, itemToAdd);
    }
}
