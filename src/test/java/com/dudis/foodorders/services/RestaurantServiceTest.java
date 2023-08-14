package com.dudis.foodorders.services;

import com.dudis.foodorders.api.mappers.*;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantDAO restaurantDAO;
    @Mock
    private DeliveryAddressService deliveryAddressService;
    @Mock
    private BillService billService;
    @Mock
    private OrderService orderService;
    @Mock
    private FoodService foodService;
    @Mock
    private StorageService storageService;
    @Mock
    private PageableService pageableService;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private MenuMapper menuMapper;
    @Mock
    private FoodMapper foodMapper;
    @Mock
    private DeliveryAddressMapper deliveryAddressMapper;
    @Mock
    private OrderMapper orderMapper;

    @Test
    void findOwnerLocalsWorksCorrectly() {

    }
    @Test
    void addLocalWorksCorrectly() {

    }
    @Test
    void findProcessingRestaurantWorksCorrectly() {

    }
    @Test
    void getCurrentMenuWorksCorrectly() {

    }
    @Test
    void findAllOwnerPendingOrdersWorksCorrectly() {

    }
    @Test
    void getDeliveryAddressesWorksCorrectly() {

    }
    @Test
    void findOrdersByInProgressWorksCorrectly() {

    }
    @Test
    void findPayedOrdersAndNotRealizedWorksCorrectly() {

    }
    @Test
    void addFoodToMenuWorksCorrectly() {

    }
    @Test
    void updateMenuPositionWorksCorrectly() {

    }
    @Test
    void deleteFoodFromMenuWorksCorrectly() {

    }
    @Test
    void getPaginatedMenuWorksCorrectly() {

    }
    @Test
    void getPaginatedDeliveryAddressesWorksCorrectly() {

    }
    @Test
    void addDeliveryAddressWorksCorrectly() {

    }
    @Test
    void deleteAddressFromRestaurantWorksCorrectly() {

    }
    @Test
    void findRestaurantByMenuWorksCorrectly() {

    }
    @Test
    void realizeOrderWorksCorrectly() {

    }
    @Test
    void findAllRestaurantsWorksCorrectly() {

    }
    @Test
    void findAllRestaurantsByParametersWorksCorrectly() {

    }

}