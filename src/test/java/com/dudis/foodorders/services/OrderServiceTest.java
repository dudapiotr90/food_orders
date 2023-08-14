package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.CustomerMapper;
import com.dudis.foodorders.api.mappers.OffsetDateTimeMapper;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.domain.exception.OrderException;
import com.dudis.foodorders.services.dao.OrderDAO;
import com.dudis.foodorders.utils.RestaurantUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.dudis.foodorders.utils.CartUtils.someCart;
import static com.dudis.foodorders.utils.CustomerUtils.*;
import static com.dudis.foodorders.utils.OrderItemsUtils.someOrderItems;
import static com.dudis.foodorders.utils.OrderItemsUtils.someSetOfOrderItemsDTO;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.someRestaurant1DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private OrderItemService orderItemService;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private OffsetDateTimeMapper offsetDateTimeMapper;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private PageableService pageableService;

    @Test
    void findOrdersByInProgressWorksCorrectly() {
        // Given
        List<Order> expected = someOrders();
        when(orderDAO.getRestaurantOrders(anyInt(), anyBoolean())).thenReturn(expected);

        // When
        List<Order> result = orderService.findOrdersByInProgress(123, true);

        // Then
        assertEquals(expected, result);
        verify(orderDAO, times(1)).getRestaurantOrders(anyInt(), anyBoolean());
    }

    @Test
    void findCancelableOrdersWorksCorrectly() {
        // Given
        List<Order> expected = someOrders();
        when(orderDAO.findCancelableOrders(anyInt())).thenReturn(expected);

        // When
        List<Order> result = orderService.findCancelableOrders(123);

        // Then
        assertEquals(expected, result);
        verify(orderDAO, times(1)).findCancelableOrders(anyInt());
    }

    @Test
    void countPendingOrdersForRestaurantWorksCorrectly() {
        // Given
        Integer expected = 6;
        Restaurant someRestaurant = someRestaurant1();
        when(orderDAO.findPendingOrdersForRestaurant(any(Restaurant.class))).thenReturn(expected);

        // When
        Integer result = orderService.countPendingOrdersForRestaurant(someRestaurant);

        // Then
        assertEquals(expected, result);
        verify(orderDAO, times(1)).findPendingOrdersForRestaurant(any(Restaurant.class));
    }

    @Test
    void makeAnOrderWorksCorrectly() {
        // Given
        Set<OrderItem> someItems = someOrderItems();
        String comment = "some comment";
        RestaurantDTO someRestaurantDTO = someRestaurant1DTO();
        Customer someCustomer = someCustomer();
        Order expected = someOrder1();
        when(restaurantMapper.mapFromDTO(any(RestaurantDTO.class))).thenReturn(someRestaurant1());
        when(orderDAO.issueAnOrder(any(Order.class))).thenReturn(expected);

        // When
        Order result = orderService.makeAnOrder(someItems, comment, someRestaurantDTO, someCustomer);

        // Then
        assertEquals(expected, result);
        verify(orderDAO, times(1)).issueAnOrder(any(Order.class));
        verify(restaurantMapper, times(1)).mapFromDTO(any(RestaurantDTO.class));

    }

    @Test
    void cancelOrderWorksCorrectly() {
        // Given
        String someOrderNumber = UUID.randomUUID().toString();
        Cart someCart = someCart();
        Order someOrder = someOrder1().withCancelTill(OffsetDateTime.MAX);
        when(orderDAO.findByOrderNumber(anyString())).thenReturn(Optional.of(someOrder));
        doNothing().when(orderItemService).returnOrderItemsToCartAndUncheckOrder(anySet(), any(Cart.class));
        doNothing().when(orderDAO).cancelOrder(any(Order.class));

        // When
        orderService.cancelOrder(someOrderNumber, someCart);

        // Then
        verify(orderDAO, times(1)).findByOrderNumber(anyString());
        verify(orderItemService, times(1)).returnOrderItemsToCartAndUncheckOrder(anySet(), any(Cart.class));
        verify(orderDAO, times(1)).cancelOrder(any(Order.class));


    }

    @Test
    void cancelOrderThrowsCorrectly() {
        // Given
        String someOrderNumber = UUID.randomUUID().toString();
        Cart someCart = someCart();
        Order someOrder = someOrder2().withCancelTill(OffsetDateTime.now().minusMinutes(5));
        when(orderDAO.findByOrderNumber(anyString())).thenReturn(Optional.empty());
        when(orderDAO.findByOrderNumber(someOrder.getOrderNumber())).thenReturn(Optional.of(someOrder));

        String expectedMessage1 = "Order with order number: [%s] doesn't exist".formatted(someOrderNumber);
        String expectedMessage2 = "You can't cancel order now. It was possible till: [%s]"
            .formatted(someOrder.getCancelTill());

        // When
        Throwable result1 = assertThrows(NotFoundException.class, () -> orderService.cancelOrder(someOrderNumber, someCart));
        Throwable result2 = assertThrows(OrderException.class,
            () -> orderService.cancelOrder(someOrder.getOrderNumber(), someCart));

        // Then
        assertEquals(expectedMessage1, result1.getMessage());
        assertEquals(expectedMessage2, result2.getMessage());
    }

    @Test
    void findOrderByOrderNumberWorksCorrectly() {
        // Given
        String someOrderNumber = UUID.randomUUID().toString();
        Order expected = someOrder1();
        when(orderDAO.findByOrderNumber(anyString())).thenReturn(Optional.of(expected));
        String throwingOrderNumber = someOrder2().getOrderNumber();
        when(orderDAO.findByOrderNumber(throwingOrderNumber)).thenReturn(Optional.empty());
        String expectedMessage = "Order with order number: [%s] doesn't exist".formatted(throwingOrderNumber);

        // When
        Order result = orderService.findOrderByOrderNumber(someOrderNumber);
        Throwable exception = assertThrows(NotFoundException.class,
            () -> orderService.findOrderByOrderNumber(throwingOrderNumber));

        // Then
        assertEquals(expected, result);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void setOrderAsInProgressWorksCorrectly() {
        // Given
        Order someOrder = someOrder1();
        doNothing().when(orderDAO).setOrderAsInProgress(any(Order.class));

        // When
        orderService.setOrderAsInProgress(someOrder);

        // Then
        verify(orderDAO, times(1)).setOrderAsInProgress(any(Order.class));
    }

    @Test
    void realizeOrderWorksCorrectly() {
        // Given
        String someOrderNumber = UUID.randomUUID().toString();
        Restaurant someRestaurant = someRestaurant1();
        String throwingOrderNumber = "throwingOrderNumber";
        when(orderDAO.findByOrderNumber(anyString())).thenReturn(Optional.of(someOrder1()));
        when(orderDAO.findByOrderNumber(throwingOrderNumber)).thenReturn(Optional.empty());
        doNothing().when(orderDAO).realizeOrder(any(Order.class));
        String expectedMessage = "Order with order number: [%s] doesn't exist. Can't realize request"
            .formatted(throwingOrderNumber);

        // When
        orderService.realizeOrder(someOrderNumber, someRestaurant);
        Throwable exception = assertThrows(NotFoundException.class,
            () -> orderService.realizeOrder(throwingOrderNumber, someRestaurant));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
        verify(orderDAO, times(2)).findByOrderNumber(anyString());
        verify(orderDAO, times(1)).realizeOrder(any(Order.class));
    }

    @Test
    void getPaginatedRealizedOwnerOrdersWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(1, 2);
        Page<Order> somePagedOrders = new PageImpl<>(someOrders());
        String someODT1 = "2023-08-14T12:00+03:00";
        String someODT2 = "2023-08-16T12:32+08:00";
        stubPageable(pageable);
        when(orderDAO.getPaginatedRealizedOwnerOrders(anyList(), anyBoolean(), any(Pageable.class)))
            .thenReturn(somePagedOrders);
        when(orderItemMapper.mapOrderItemsToDTO(anySet())).thenReturn(someSetOfOrderItemsDTO());
        when(offsetDateTimeMapper.mapOffsetDateTimeToString(any(OffsetDateTime.class))).thenReturn(someODT1, someODT2);
        when(customerMapper.mapToDTO(null)).thenReturn(someCustomerDTO());
        when(orderDAO.findRestaurantByOrderNumber(anyString())).thenReturn(someRestaurant1());
        Page<OrderDTO> expected = new PageImpl<>(someOrdersDTO());

        // When
        Page<OrderDTO> result = orderService.getPaginatedRealizedOwnerOrders(List.of(1, 3, 8), 3, 6, "asc", "orderNumber");

        // Then
        assertEquals(expected.getSize(),result.getSize());


    }

    @Test
    void getPaginatedRealizedCustomerOrdersWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(1, 2);
        stubPageable(pageable);
        Page<Order> expected = new PageImpl<>(someOrders());
        when(orderDAO.getPaginatedRealizedCustomerOrders(anyInt(), anyBoolean(), any(Pageable.class))).thenReturn(expected);

        // When
        Page<Order> result = orderService.getPaginatedRealizedCustomerOrders(4, 3, 6, "asc", "orderNumber");

        // Then
        assertEquals(expected.getSize(),result.getSize());
    }

    private void stubPageable(Pageable pageable) {
        when(pageableService.preparePageable(anyInt(), anyInt(), anyString(), any(String[].class)))
            .thenReturn(pageable);
    }
}