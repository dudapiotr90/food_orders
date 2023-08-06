package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.services.dao.OrderItemDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public void updateOrderItem(OrderItem orderItem) {
        orderItemDAO.updateOrderItem(orderItem);
    }

    public void deleteOrderItem(Integer orderItemId) {
        orderItemDAO.deleteOrderItem(orderItemId);
    }

    public OrderItem findOrderItemById(Integer id) {
        return orderItemDAO.findOrderItemById(id);
    }

    public void deleteOrderItemsFromCartAndAssignOrder(Set<OrderItem> orderItems, Order order) {
        orderItems.forEach(orderItem -> orderItemDAO.setOrder(orderItem,order));
        orderItems.forEach(orderItemDAO::deleteOrderItemFromCart);
    }

    public void returnOrderItemsToCartAndUncheckOrder(Set<OrderItem> orderItems, Cart cart) {
        orderItems.forEach(orderItemDAO::setOrderNull);
        orderItems.forEach(orderItem -> orderItemDAO.setCart(orderItem,cart));
    }
}
