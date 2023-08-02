package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.services.dao.OrderItemDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemDAO orderItemDAO;
    private final FoodService foodService;
    public void addItemToCart(Cart cart, OrderItem itemToAdd) {
        orderItemDAO.addItemToCart(cart, itemToAdd);
    }

    public Menu findMenuByFood(Food food) {
        return foodService.findMenuByFood(food);
    }
}
