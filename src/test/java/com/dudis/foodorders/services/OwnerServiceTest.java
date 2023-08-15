package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.OwnerMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.OwnerDAO;
import com.dudis.foodorders.utils.OrderUtils;
import com.dudis.foodorders.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.dudis.foodorders.utils.AccountUtils.someAccount;
import static com.dudis.foodorders.utils.AccountUtils.someRegistrationRequest;
import static com.dudis.foodorders.utils.OwnerUtils.*;
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
        when(restaurantMapper.mapToDTO(any(Restaurant.class))).thenReturn(someRestaurantDTO1());
        when(deliveryAddressService.countDeliveryAddressesForRestaurant(any(Restaurant.class))).thenReturn(6);
        when(restaurantMapper.mapFromDTO(any(RestaurantDTO.class))).thenReturn(someRestaurant2());

        // When
        List<RestaurantDTO> result = ownerService.findAllOwnerRestaurants(3);

        // Then
        assertEquals(expected.size(),result.size());
    }
    @Test
    void addRestaurantWorksCorrectly() {
        // Given
        RestaurantDTO someRestaurant = someRestaurantDTO1();
        String expectedMessage = "Owner doesn't exists";
        when(restaurantMapper.mapFromDTO(any(RestaurantDTO.class))).thenReturn(someRestaurant1());
        when(ownerDAO.findOwnerById(anyInt())).thenReturn(Optional.of(someOwner1()));
        when(ownerDAO.findOwnerById(6)).thenReturn(Optional.empty());
        doNothing().when(restaurantService).addLocal(any(Restaurant.class));

        // When
        Throwable exception = assertThrows(NotFoundException.class,
            () -> ownerService.addRestaurant(6, someRestaurant));
        ownerService.addRestaurant(234,someRestaurant);

        // Then
        assertEquals(expectedMessage,exception.getMessage());
        verify(restaurantService,times(1)).addLocal(any(Restaurant.class));
        verify(restaurantMapper,times(2)).mapFromDTO(any(RestaurantDTO.class));
        verify(ownerDAO,times(2)).findOwnerById(anyInt());
    }
    @Test
    void findOwnerByAccountIdWorksCorrectly() {
        // Given
        Owner owner1 = someOwner1().withAccount(someAccount());
        OwnerDTO expected = someOwnerDTO1();
        String expectedMessage = "Owner doesn't exists";
        when(ownerDAO.findOwnerByAccountId(anyInt())).thenReturn(Optional.of(owner1));
        when(ownerDAO.findOwnerByAccountId(3)).thenReturn(Optional.empty());
        when(ownerMapper.mapToDTO(any(Owner.class))).thenReturn(expected);

        // When
        OwnerDTO result = ownerService.findOwnerByAccountId(234);
        Throwable exception = assertThrows(NotFoundException.class,
            () -> ownerService.findOwnerByAccountId(3));

        // Then
        assertEquals(expected, result);
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    void findOwnerByIdWorksCorrectly() {
        // Given
        Owner owner = someOwner1();
        OwnerDTO expected = someOwnerDTO1();
        String expectedMessage = "Owner doesn't exists";
        when(ownerDAO.findOwnerById(anyInt())).thenReturn(Optional.of(owner));
        when(ownerDAO.findOwnerById(5)).thenReturn(Optional.empty());
        when(ownerMapper.mapToDTO(any(Owner.class))).thenReturn(expected);

        // When
        OwnerDTO result = ownerService.findOwnerById(345);
        Throwable exception = assertThrows(NotFoundException.class, () -> ownerService.findOwnerById(5));

        // Then
        assertEquals(expected, result);
        assertEquals(expectedMessage, exception.getMessage());
    }
    @Test
    void findOwnerRealizedOrdersWorksCorrectly() {
        // Given
        Page<OrderDTO> expected = new PageImpl<>(OrderUtils.someListOfOrderDTO());
        when(restaurantService.findOwnerLocals(anyInt())).thenReturn(someRestaurants());
        when(orderService.getPaginatedRealizedOwnerOrders(anyList(), anyInt(), anyInt(), anyString(), anyString()))
            .thenReturn(expected);

        // When
        Page<OrderDTO> result = ownerService.findOwnerRealizedOrders(1, 1, 5, "orderNumber", "asc");

        // Then
        assertEquals(expected.getSize(),result.getSize());
    }
    @Test
    void findAllOwnersWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(1, 5);
        Page<OwnerDTO> expected = new PageImpl<>(someOwnersDTO());
        Page<Owner> pagedOwners = new PageImpl<>(someOwners());
        when(pageableService.preparePageable(anyInt(), anyInt(), anyString(), anyString())).thenReturn(pageable);
        when(ownerDAO.findAllOwners(any(Pageable.class))).thenReturn(pagedOwners);
        when(ownerMapper.mapToDTO(any(Owner.class))).thenReturn(someOwnerDTO1(),someOwnerDTO2(),someOwnerDTO3());

        // When
        Page<OwnerDTO> result = ownerService.findAllOwners("name", "asc", 4, 1);

        // Then
        assertEquals(expected.getSize(),result.getSize());
        assertEquals(expected,result);
    }
}