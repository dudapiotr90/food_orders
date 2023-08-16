package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import com.dudis.foodorders.infrastructure.database.mappers.CartEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderItemEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OrderItemJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dudis.foodorders.utils.CartUtils.someCart;
import static com.dudis.foodorders.utils.CartUtils.someCartEntity;
import static com.dudis.foodorders.utils.OrderItemsUtils.someOrderItem1;
import static com.dudis.foodorders.utils.OrderItemsUtils.someOrderItemEntity1;
import static com.dudis.foodorders.utils.OrderUtils.someOrder1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemRepositoryTest {

    @InjectMocks
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderItemJpaRepository orderItemJpaRepository;
    @Mock
    private OrderItemEntityMapper orderItemEntityMapper;
    @Mock
    private CartEntityMapper cartEntityMapper;

    @Test
    void addItemToCartWorksCorrectly() {
       // Given
        Cart someCart = someCart();
        Cart someCart2 = someCart().withCartId(312);
        OrderItem someItem = someOrderItem1();
        when(orderItemJpaRepository.findAllByFoodIdAndCartId(anyInt(), anyInt()))
            .thenReturn(Optional.of(someOrderItemEntity1()))
            .thenReturn(Optional.empty());
        when(orderItemEntityMapper.mapFromEntity(any(OrderItemEntity.class)))
            .thenReturn(someItem);
        when(orderItemJpaRepository.findById(someItem.getOrderItemId()))
            .thenReturn(Optional.of(someOrderItemEntity1()));
        when(orderItemJpaRepository.save(someOrderItemEntity1()))
            .thenReturn(someOrderItemEntity1());
        when(orderItemEntityMapper.mapToEntity(any(OrderItem.class)))
            .thenReturn(someOrderItemEntity1());
        when(cartEntityMapper.mapToEntity(any(Cart.class)))
            .thenReturn(someCartEntity());

        // When
        orderItemRepository.addItemToCart(someCart,someItem);
        orderItemRepository.addItemToCart(someCart2,someItem);
        // Then
        verify(orderItemJpaRepository, times(2)).findAllByFoodIdAndCartId(anyInt(), anyInt());
        verify(orderItemEntityMapper, times(1)).mapFromEntity(any(OrderItemEntity.class));
        verify(orderItemEntityMapper, times(1)).mapToEntity(any(OrderItem.class));
        verify(cartEntityMapper, times(1)).mapToEntity(any(Cart.class));
        verify(orderItemJpaRepository, times(1)).findById(someItem.getOrderItemId());
        verify(orderItemJpaRepository, times(2)).save(any(OrderItemEntity.class));
    }

    @Test
    void updateOrderItemWorksCorrectly() {
        // Given
        OrderItem someItem = someOrderItem1();
        String expectedMessage = "OrderItem with id";
        when(orderItemJpaRepository.findById(someItem.getOrderItemId()))
            .thenReturn(Optional.of(someOrderItemEntity1()))
            .thenReturn(Optional.empty());
        when(orderItemJpaRepository.save(any(OrderItemEntity.class)))
            .thenReturn(someOrderItemEntity1());

        // When
        orderItemRepository.updateOrderItem(someItem);
        Throwable exception = assertThrows(EntityNotFoundException.class,
            () -> orderItemRepository.updateOrderItem(someItem));
        // Then
        assertTrue(exception.getMessage().contains(expectedMessage));
        verify(orderItemJpaRepository, times(2)).findById(someItem.getOrderItemId());
        verify(orderItemJpaRepository, times(1)).save(any(OrderItemEntity.class));
    }

    @Test
    void deleteOrderItemWorksCorrectly() {
        // Given
        Integer someId = 342;
        doNothing().when(orderItemJpaRepository).deleteById(anyInt());

        // When, Then
        orderItemRepository.deleteOrderItem(someId);
        verify(orderItemJpaRepository,times(1)).deleteById(anyInt());
    }

    @Test
    void findOrderItemByIdWorksCorrectly() {
        // Given
        Integer someId = 342;
        OrderItem expected = someOrderItem1();
        when(orderItemJpaRepository.findById(anyInt()))
            .thenReturn(Optional.of(someOrderItemEntity1()))
            .thenReturn(Optional.empty());
        when(orderItemEntityMapper.mapFromEntity(someOrderItemEntity1()))
            .thenReturn(expected);

        // When
        OrderItem result1 = orderItemRepository.findOrderItemById(someId);
        OrderItem result2 = orderItemRepository.findOrderItemById(someId);

        // Then
        assertEquals(expected,result1);
        assertNull(result2);

    }

    @Test
    void deleteOrderItemFromCart() {
        // Given
        OrderItem someOrderItem = someOrderItem1();
        when(orderItemEntityMapper.mapToEntity(someOrderItem))
            .thenReturn(someOrderItemEntity1());
        doNothing().when(orderItemJpaRepository).deleteOrderItemFromCart(anyInt());

        // When, Then
        orderItemRepository.deleteOrderItemFromCart(someOrderItem);
        verify(orderItemEntityMapper, times(1)).mapToEntity(any(OrderItem.class));
        verify(orderItemJpaRepository,times(1)).deleteOrderItemFromCart(anyInt());
    }

    @Test
    void setOrderWorksCorrectly() {
        // Given
        Integer orderItemId = someOrderItem1().getOrderItemId();
        Integer orderId = someOrder1().getOrderId();
        doNothing().when(orderItemJpaRepository).setOrder(orderItemId, orderId);
        // When, Then
        orderItemRepository.setOrder(someOrderItem1(), someOrder1());
        verify(orderItemJpaRepository,times(1)).setOrder(orderItemId,orderId);
    }

    @Test
    void setCartWorksCorrectly() {
        // Given
        Integer cartId = someCart().getCartId();
        Integer orderItemId = someOrderItem1().getOrderItemId();
        doNothing().when(orderItemJpaRepository).setCart(orderItemId, cartId);
        // When, Then
        orderItemRepository.setCart(someOrderItem1(), someCart());
        verify(orderItemJpaRepository,times(1)).setCart(orderItemId,cartId);
    }

    @Test
    void setOrderNullWorksCorrectly() {
        // Given
        OrderItem someOrderItem = someOrderItem1();
        doNothing().when(orderItemJpaRepository).setOrderNull(anyInt());

        // When, Then
        orderItemRepository.setOrderNull(someOrderItem);
        verify(orderItemJpaRepository,times(1)).setOrderNull(anyInt());
    }

}