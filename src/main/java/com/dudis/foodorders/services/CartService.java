package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.api.dtos.OrderRequestDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.services.dao.CartDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CartService {
    private final CartDAO cartDAO;
    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;
    private final OrderService orderService;

    public void addItemToCart(Cart cart, OrderItem itemToAdd) {
        orderItemService.addItemToCart(cart, itemToAdd);
    }
    @Transactional
    public void updateOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemMapper.mapFromDTO(orderItemDTO);
        orderItemService.updateOrderItem(orderItem);
    }

    public void deleteOrderItem(Integer orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
    }

    @Transactional
    public void issueOrder(OrderRequestDTO orderRequestDTO, RestaurantDTO restaurant, Cart cart) {

        List<Integer> orderItemsId = orderRequestDTO.getOrderItemsId();
        List<OrderItem> orderItems = orderItemsId.stream()
            .map(orderItemService::findOrderItemById)
            .filter(Objects::nonNull)
            .toList();


        orderService.makeAnOrder(orderItems, orderRequestDTO.getCustomerComment(),restaurant);

        orderItemService.deleteOrderItemsFromCart(orderItems,cart);

    }
}
