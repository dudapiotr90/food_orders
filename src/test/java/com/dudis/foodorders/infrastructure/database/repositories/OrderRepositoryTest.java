package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import com.dudis.foodorders.infrastructure.database.mappers.OrderEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderItemEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OrderJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dudis.foodorders.utils.OrderItemsUtils.someOrderItemEntities;
import static com.dudis.foodorders.utils.OrderItemsUtils.someOrderItems;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryTest {

    @InjectMocks
    private OrderRepository orderRepository;

    @Mock
    private OrderJpaRepository orderJpaRepository;
    @Mock
    private OrderEntityMapper orderEntityMapper;
    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;
    @Mock
    private OrderItemEntityMapper orderItemEntityMapper;
    @Mock
    private Clock clock;

    @Test
    void findCancelableOrdersWorksCorrectly() {
        // Given
        Integer someId = 312;
        Order order1 = someOrder1();
        Order order2 = someOrder2();

        Clock fixedClock
            = Clock.fixed(OffsetDateTime.of(1990, 8, 8, 6, 50, 0, 0, ZoneOffset.UTC).toInstant(), ZoneId.systemDefault());
        doReturn(fixedClock.instant()).when(clock).instant();
        doReturn(fixedClock.getZone()).when(clock).getZone();

        when(orderJpaRepository.findCancelableOrders(someId)).thenReturn(someOrderEntities());
        when(orderEntityMapper.mapFromEntity(any(OrderEntity.class)))
            .thenReturn(order1, order2);
        List<Order> expected = someOrders();
        // When
        List<Order> result = orderRepository.findCancelableOrders(someId);

        // Then
        assertEquals(expected, result);

    }

    @Test
    void getRestaurantOrdersWorksCorrectly() {
        // Given
        Integer someId = 123;
        boolean isInProgress = true;
        when(orderJpaRepository.findRestaurantOrdersInProgress(someId, true))
            .thenReturn(someOrderEntities());
        when(orderEntityMapper.mapFromEntity(any(OrderEntity.class)))
            .thenReturn(someOrder1(), someOrder2());
        List<Order> expected = someOrders();

        // When
        List<Order> result = orderRepository.getRestaurantOrders(someId, isInProgress);
        // Then
        assertEquals(expected,result);
    }

    @Test
    void findPendingOrdersForRestaurantWorksCorrectly() {
        // Given
        Integer expected = 147;
        when(restaurantEntityMapper.mapToEntity(any(Restaurant.class)))
            .thenReturn(someRestaurantEntity1());
        when(orderJpaRepository.countByRestaurantIdAndRealized(someRestaurantEntity1(), false))
            .thenReturn(expected);

        // When
        Integer result = orderRepository.findPendingOrdersForRestaurant(someRestaurant1());

        // Then
        assertEquals(expected,result);

    }

    @Test
    void issueAnOrderWorksCorrectly() {
        // Given
        Order expected = someOrder1();
        when(orderEntityMapper.mapToEntity(expected)).thenReturn(someOrderEntity1());
        when(orderJpaRepository.save(someOrderEntity1())).thenReturn(someOrderEntity1());
        when(orderEntityMapper.mapFromEntity(someOrderEntity1())).thenReturn(expected);

        // When, Then
        Order result = orderRepository.issueAnOrder(expected);
        assertEquals(expected,result);
    }

    @Test
    void findByOrderNumberWorksCorrectly() {
        // Given
        Optional<Order> expected = Optional.of(someOrder1());
        String someString = UUID.randomUUID().toString();
        when(orderJpaRepository.findByOrderNumber(anyString()))
            .thenReturn(Optional.of(someOrderEntity1()))
            .thenReturn(Optional.empty());
        when(orderEntityMapper.mapFromEntity(any(OrderEntity.class)))
            .thenReturn(someOrder1());

        // When
        Optional<Order> result = orderRepository.findByOrderNumber(someString);
        orderRepository.findByOrderNumber(someString);
        // Then
        assertEquals(expected,result);
        verify(orderJpaRepository, times(2)).findByOrderNumber(anyString());
        verify(orderEntityMapper, times(1)).mapFromEntity(any(OrderEntity.class));
    }

    @Test
    void setOrderAsInProgressWorksCorrectly() {
        // Given
        Order someOrder = someOrder1();
        doNothing().when(orderJpaRepository).setInProgress(anyBoolean(),anyString());

        // When, Then
        orderRepository.setOrderAsInProgress(someOrder);
        verify(orderJpaRepository,times(1)).setInProgress(anyBoolean(),anyString());
    }

    @Test
    void realizeOrderWorksCorrectly() {
        // Given
        Order someOrder = someOrder1();
        when(orderEntityMapper.mapToEntity(someOrder)).thenReturn(someOrderEntity1());
        doNothing().when(orderJpaRepository)
            .updateRealizedAndCompletedDateTime(someOrder.getOrderNumber(), someOrder.getRealized(), someOrder.getCompletedDateTime());
        // When, Then
        orderRepository.realizeOrder(someOrder);
        verify(orderEntityMapper, times(1)).mapToEntity(any(Order.class));
        verify(orderJpaRepository, times(1)).updateRealizedAndCompletedDateTime(
            someOrder.getOrderNumber(), someOrder.getRealized(), someOrder.getCompletedDateTime());
    }

    @Test
    void findRestaurantByOrderNumberWorksCorrectly() {
        // Given
        List<Object[]> restaurantDetails = List.of(someRestaurantAsObject1(),new Object[0]);
        String someString = UUID.randomUUID().toString();
        when(orderJpaRepository.findRestaurantByOrderNumber(someString))
            .thenReturn(restaurantDetails);
        Restaurant expected = someRestaurant1();

        // When
        Restaurant result = orderRepository.findRestaurantByOrderNumber(someString);

        // Then
        assertEquals(expected.getRestaurantId(),result.getRestaurantId());
        assertEquals(expected.getType(),result.getType());
        assertEquals(expected.getName(),result.getName());
    }

    @Test
    void getPaginatedRealizedOwnerOrdersWorksCorrectly() {
        // Given
        Pageable somePageable = PageRequest.of(4, 12);
        List<Integer> someIds = List.of(5, 9, 2);
        boolean realized = true;
        Page<OrderEntity> someOrders = new PageImpl<>(someOrderEntities());
        Page<Order> expected = new PageImpl<>(someOrders());

        when(orderJpaRepository.findByRestaurantIds(someIds, realized, somePageable))
            .thenReturn(someOrders);
        when(orderEntityMapper.mapFromEntity(any(OrderEntity.class)))
            .thenReturn(someOrder1(), someOrder2());

        // When
        Page<Order> result = orderRepository.getPaginatedRealizedOwnerOrders(someIds, realized, somePageable);

        // Then
        assertEquals(expected,result);
    }

    @Test
    void getPaginatedRealizedCustomerOrdersWorksCorrectly() {
        // Given
        Pageable somePageable = PageRequest.of(4, 12);
        Integer someId = 3;
        boolean realized = true;
        Page<OrderEntity> someOrders = new PageImpl<>(someOrderEntities());
        Page<Order> expected = new PageImpl<>(someOrders());
        when(orderJpaRepository.findByCustomerIdAndRealized(someId, realized, somePageable))
            .thenReturn(someOrders);
        when(orderItemEntityMapper.mapOrderItemsFromEntity(someOrderItemEntities()))
            .thenReturn(someOrderItems());

        // When
        Page<Order> result = orderRepository.getPaginatedRealizedCustomerOrders(someId, realized, somePageable);

        // Then
        assertEquals(expected.getSize(),result.getSize());
    }

    @Test
    void cancelOrderWorksCorrectly() {
        // Given
        Order someOrder = someOrder1();
        doNothing().when(orderJpaRepository).deleteByOrderNumber(anyString());

        // When, Then
        orderRepository.cancelOrder(someOrder);
        verify(orderJpaRepository,times(1)).deleteByOrderNumber(anyString());
    }
}