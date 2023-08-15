package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.services.dao.OrderItemDAO;
import com.dudis.foodorders.utils.FoodUtils;
import com.dudis.foodorders.utils.MenuUtils;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.dudis.foodorders.utils.CartUtils.someCart;
import static com.dudis.foodorders.utils.OrderItemsUtils.someOrderItem1;
import static com.dudis.foodorders.utils.OrderItemsUtils.someOrderItems;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @InjectMocks
    private OrderItemService orderItemService;

    @Mock
    private OrderItemDAO orderItemDAO;
    @Mock
    private FoodService foodService;

    @Test
    void addItemToCartWorksCorrectly() {
        // Given
        Cart someCart = someCart();
        OrderItem someItem = someOrderItem1();
        doNothing().when(orderItemDAO).addItemToCart(any(Cart.class),any(OrderItem.class));
        // When
        orderItemService.addItemToCart(someCart,someItem);

        // Then
        verify(orderItemDAO,times(1)).addItemToCart(any(Cart.class),any(OrderItem.class));
    }
    @Test
    void findMenuByFoodWorksCorrectly() {
        // Given
        Food someFood = FoodUtils.someFood1();
        Menu expected = MenuUtils.someMenu1();
        when(foodService.findMenuByFood(any(Food.class))).thenReturn(expected);
        // When
        Menu result = orderItemService.findMenuByFood(someFood);

        // Then
        verify(foodService,times(1)).findMenuByFood(any(Food.class));
        assertEquals(expected,result);
    }
    @Test
    void updateOrderItemWorksCorrectly() {
        // Given
        OrderItem someItem = someOrderItem1();
        doNothing().when(orderItemDAO).updateOrderItem(any(OrderItem.class));
        // When
        orderItemService.updateOrderItem(someItem);

        // Then
        verify(orderItemDAO,times(1)).updateOrderItem(any(OrderItem.class));
    }
    @Test
    void deleteOrderItemWorksCorrectly() {
        // Given
        Integer someItemId = 543;
        doNothing().when(orderItemDAO).deleteOrderItem(anyInt());
        // When
        orderItemService.deleteOrderItem(someItemId);

        // Then
        verify(orderItemDAO,times(1)).deleteOrderItem(anyInt());
    }
    @Test
    void findOrderItemByIdWorksCorrectly() {
        // Given
        Integer someItemId = 543;
        OrderItem expected = someOrderItem1();
        String expectedMessage = "Wrong input";
        when(orderItemDAO.findOrderItemById(anyInt())).thenReturn(expected);
//         When
        OrderItem result = orderItemService.findOrderItemById(someItemId);
        Throwable exception = assertThrows(ValidationException.class, () -> orderItemService.findOrderItemById(null));

        //Then
        verify(orderItemDAO,times(1)).findOrderItemById(anyInt());
        assertEquals(expected,result);

        assertEquals(expectedMessage,exception.getMessage());
    }
    @Test
    void deleteOrderItemsFromCartAndAssignOrderWorksCorrectly() {
        // Given
        Set<OrderItem> someItems = someOrderItems();
        Order someOrder = someOrder1();
        doNothing().when(orderItemDAO).setOrder(any(OrderItem.class),any(Order.class));
        doNothing().when(orderItemDAO).deleteOrderItemFromCart(any(OrderItem.class));

        // When
        orderItemService.deleteOrderItemsFromCartAndAssignOrder(someItems,someOrder);

        // Then
        verify(orderItemDAO,times(someItems.size())).setOrder(any(OrderItem.class),any(Order.class));
        verify(orderItemDAO,times(someItems.size())).deleteOrderItemFromCart(any(OrderItem.class));

    }
    @Test
    void returnOrderItemsToCartAndUncheckOrderWorksCorrectly() {
        // Given
        Set<OrderItem> someItems = someOrderItems();
        Cart someCart = someCart();
        doNothing().when(orderItemDAO).setOrderNull(any(OrderItem.class));
        doNothing().when(orderItemDAO).setCart(any(OrderItem.class),any(Cart.class));

        // When
        orderItemService.returnOrderItemsToCartAndUncheckOrder(someItems,someCart);

        // Then
        verify(orderItemDAO,times(someItems.size())).setOrderNull(any(OrderItem.class));
        verify(orderItemDAO,times(someItems.size())).setCart(any(OrderItem.class),any(Cart.class));
    }
}