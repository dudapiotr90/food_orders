package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.api.dtos.OrderRequestDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.CustomerMapper;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.services.dao.CartDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {
    private final CartDAO cartDAO;
    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final BillService billService;
    private final OrderItemMapper orderItemMapper;
    private final CustomerMapper customerMapper;

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
    public String issueOrder(OrderRequestDTO orderRequestDTO, RestaurantDTO restaurant, CustomerDTO customerDTO) {
        Customer customer = customerMapper.mapFromDTO(customerDTO);
        Set<Integer> orderItemsId = orderRequestDTO.getOrderItemsId();
        Set<OrderItem> orderItems = orderItemsId.stream()
            .map(orderItemService::findOrderItemById)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());


        Order order = orderService.makeAnOrder(orderItems, orderRequestDTO.getCustomerComment(), restaurant,customer);

        orderItemService.deleteOrderItemsFromCartAndAssignOrder(orderItems,order);

//        Bill bill = billService.issueReceipt(order, customer, restaurant);
        return order.getOrderNumber();
// TODO maybe issueReceipt after order cant be cancelled
    }
}
