package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.OwnerMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.OwnerDAO;
import com.dudis.foodorders.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.dudis.foodorders.utils.AccountUtils.someAccount;
import static com.dudis.foodorders.utils.AccountUtils.someRegistrationRequest;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @InjectMocks
    private OwnerService ownerService;

    @Mock
    private OwnerDAO ownerDAO;
    @Mock
    private AccountService accountService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private DeliveryAddressService deliveryAddressService;
    @Mock
    private PageableService pageableService;
    @Mock
    private OrderService orderService;
    @Mock
    private OwnerMapper ownerMapper;
    @Mock
    private RestaurantMapper restaurantMapper;

    @Test
    void registerOwnerWorksCorrectly() {
        // Given
        ConfirmationToken expected = TokenUtils.someToken();
        RegistrationRequest someRequest = someRegistrationRequest();
        Account someAccount = someAccount().withRoleId(2);
        when(accountService.buildAccount(someRequest)).thenReturn(someAccount);
        when(ownerDAO.registerOwner(any(Owner.class))).thenReturn(expected);
        // When
        ConfirmationToken result = ownerService.registerOwner(someRequest);

        // Then
        verify(accountService, times(1)).buildAccount(someRequest);
        verify(ownerDAO, times(1)).registerOwner(any(Owner.class));
        assertEquals(expected, result);
    }
    @Test
    void findAllOwnerRestaurantsWorksCorrectly() {
        // Given
        List<RestaurantDTO> expected = someRestaurantsDTO();
        List<Restaurant> restaurants = someRestaurants();
        when(restaurantService.findOwnerLocals(anyInt())).thenReturn(restaurants);
        when(restaurantMapper.mapToDTO(any(Restaurant.class))).thenReturn(someRestaurant1DTO());
        when(deliveryAddressService.countDeliveryAddressesForRestaurant(any(Restaurant.class))).thenReturn(6);
        when(restaurantMapper.mapFromDTO(any(RestaurantDTO.class))).thenReturn(someRestaurant2());

        // When
        List<RestaurantDTO> result = ownerService.findAllOwnerRestaurants(3);

        // Then
        assertEquals(expected.size(),result.size());
    }
    @Test
    void addRestaurantWorksCorrectly() {

    }
    @Test
    void findOwnerByAccountIdWorksCorrectly() {

    }
    @Test
    void findOwnerByIdWorksCorrectly() {

    }
    @Test
    void findOwnerByRestaurantWorksCorrectly() {

    }
    @Test
    void findOwnerRealizedOrdersWorksCorrectly() {

    }
    @Test
    void findAllOwnersWorksCorrectly() {

    }
}