package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.services.dao.OrderItemDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemDAO orderItemDAO;
    private final FoodService foodService;
    private final OrderService orderService;
    public void addItemToCart(Cart cart, OrderItem itemToAdd) {
        orderItemDAO.addItemToCart(cart, itemToAdd);
    }

    public Menu findMenuByFood(Food food) {
        return foodService.findMenuByFood(food);
    }

    public void updateOrderItem(OrderItem orderItem) {
        orderItemDAO.updateOrderItem(orderItem);
    }

    public void deleteOrderItem(Integer orderItemId) {
        orderItemDAO.deleteOrderItem(orderItemId);
    }

    public OrderItem findOrderItemById(Integer id) {
        return orderItemDAO.findOrderItemById(id);
    }

    public void deleteOrderItemsFromCart(List<OrderItem> orderItems, Cart cart) {
        orderItems
            .forEach(oi -> orderItemDAO.deleteOrderItemFromCart(oi,cart));
    }
}
