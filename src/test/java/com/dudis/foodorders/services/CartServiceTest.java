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
import com.dudis.foodorders.utils.CartUtils;
import com.dudis.foodorders.utils.CustomerUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.dudis.foodorders.utils.CustomerUtils.someCustomer;
import static com.dudis.foodorders.utils.OrderItemsUtils.*;
import static com.dudis.foodorders.utils.OrderUtils.someOrder1;
import static com.dudis.foodorders.utils.OrderUtils.someOrderRequestDTO;
import static com.dudis.foodorders.utils.RestaurantUtils.someRestaurant1DTO;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private OrderItemService orderItemService;
    @Mock
    private OrderService orderService;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private CustomerMapper customerMapper;

    @Test
    void addItemToCartWorkCorrectly() {
        // Given, When
        Cart someCart = CartUtils.someCart();
        OrderItem someOrderItem = someOrderItem1().withQuantity(BigDecimal.valueOf(11));
        doNothing().when(orderItemService).addItemToCart(someCart,someOrderItem);

        cartService.addItemToCart(someCart,someOrderItem);

        // Then
        verify(orderItemService, times(1)).addItemToCart(any(Cart.class),any(OrderItem.class));
    }

    @Test
    void updateOrderItemWorkCorrectly() {
        // Given, When
        OrderItemDTO someOrderItemDTO = someOrderItemDTO1();
        OrderItem someOrderItem = someOrderItem1().withQuantity(BigDecimal.valueOf(11));
        when(orderItemMapper.mapFromDTO(someOrderItemDTO)).thenReturn(someOrderItem);
        doNothing().when(orderItemService).updateOrderItem(someOrderItem);

        cartService.updateOrderItem(someOrderItemDTO);

        // Then
        verify(orderItemService, times(1)).updateOrderItem(any(OrderItem.class));
        verify(orderItemMapper, times(1)).mapFromDTO(any(OrderItemDTO.class));
    }

    @Test
    void deleteOrderItemWorkCorrectly() {
        // Given, When
        doNothing().when(orderItemService).deleteOrderItem(anyInt());

        cartService.deleteOrderItem(anyInt());

        // Then
        verify(orderItemService, times(1)).deleteOrderItem(anyInt());
    }

    @Test
    void issueOrderWorkCorrectly() {
        // Given
        OrderRequestDTO orderRequestDTO = someOrderRequestDTO();
        RestaurantDTO someRestaurantDTO = someRestaurant1DTO();
        CustomerDTO someCustomerDTO = CustomerUtils.someCustomerDTO();
        Customer someCustomer = someCustomer();
        Order someOrder = someOrder1();
        when(customerMapper.mapFromDTO(any(CustomerDTO.class))).thenReturn(someCustomer);
        when(orderItemService.findOrderItemById(anyInt())).thenReturn(someOrderItem1(),null,someOrderItem2());
        when(orderService.makeAnOrder(someOrderItems(), orderRequestDTO.getCustomerComment(), someRestaurantDTO, someCustomer))
            .thenReturn(someOrder);
        doNothing().when(orderItemService).deleteOrderItemsFromCartAndAssignOrder(someOrderItems(),someOrder);
        // When
        String result = cartService.issueOrder(orderRequestDTO, someRestaurantDTO, someCustomerDTO);

        // Then
        Assertions.assertEquals(someOrder.getOrderNumber(),result);
        verify(orderItemService, times(orderRequestDTO.getOrderItemsId().size())).findOrderItemById(anyInt());
        verify(orderService, times(1)).makeAnOrder(anySet(),any(String.class),any(RestaurantDTO.class),any(Customer.class));
        verify(orderItemService, times(1)).deleteOrderItemsFromCartAndAssignOrder(someOrder.getOrderItems(),someOrder);
    }


}