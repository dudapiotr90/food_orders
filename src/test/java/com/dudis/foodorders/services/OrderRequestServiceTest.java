package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.dtos.OrderDetailsDTO;
import com.dudis.foodorders.api.mappers.FoodMapper;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.utils.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.dudis.foodorders.utils.CartUtils.*;
import static com.dudis.foodorders.utils.FoodUtils.*;
import static com.dudis.foodorders.utils.OrderItemsUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderRequestServiceTest {

    @InjectMocks
    private OrderRequestService orderRequestService;
    @Mock
    private MenuService menuService;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private FoodMapper foodMapper;

    @Test
    void prepareOrderRequestsWorksCorrectly() {
        // Given
        Cart someCart = someCart();
        Set<Restaurant> someRestaurants = Set.of(someRestaurant1(),someRestaurant2(),someRestaurant3());
        List<OrderDetailsDTO> expected = OrderUtils.someOrderDetailsList();
        when(restaurantMapper.mapToDTOForCustomer(any(Restaurant.class)))
            .thenReturn(someRestaurantForCustomerDTO1(), someRestaurantForCustomerDTO2());
        when(orderItemMapper.mapToDTO(any(OrderItem.class))).thenReturn(someOrderItemDTO1(), someOrderItemDTO2(),someOrderItemDTO3());
        when(foodMapper.mapFromDTO(any(FoodDTO.class))).thenReturn(someFood1(), someFood2(), someFood3());
        when(menuService.menuContainsFood(any(Food.class), any(Menu.class))).thenReturn(true);
        // When

        List<OrderDetailsDTO> result = orderRequestService.prepareOrderRequests(someCart, someRestaurants);

        // Then
        assertEquals(expected.size(),result.size());
    }
}