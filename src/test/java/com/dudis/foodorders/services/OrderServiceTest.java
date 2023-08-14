package com.dudis.foodorders.services;

import com.dudis.foodorders.api.mappers.*;
import com.dudis.foodorders.services.dao.OrderDAO;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
    private OrderMapper orderMapper;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private OffsetDateTimeMapper offsetDateTimeMapper;
    @Mock
    private CustomerMapper customerMapper;

    @Test
    void findOrdersByInProgressWorksCorrectly() {

    }
}