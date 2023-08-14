package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.api.mappers.DeliveryAddressMapper;
import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.services.dao.DeliveryAddressDAO;
import com.dudis.foodorders.utils.AccountUtils;
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

import static com.dudis.foodorders.utils.DeliveryAddressUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryAddressServiceTest {

    @InjectMocks
    private DeliveryAddressService deliveryAddressService;

    @Mock
    private DeliveryAddressDAO deliveryAddressDAO;
    @Mock
    private DeliveryAddressMapper deliveryAddressMapper;

    @Test
    void getRestaurantDeliveryAddressesWorksCorrectly() {
        // Given
        List<DeliveryAddress> expected = someAddresses();
        when(deliveryAddressDAO.getRestaurantDeliveryAddresses(anyInt())).thenReturn(expected);

        // When
        List<DeliveryAddress> result = deliveryAddressService.getRestaurantDeliveryAddresses(3);

        // Then
        verify(deliveryAddressDAO, times(1)).getRestaurantDeliveryAddresses(anyInt());
        assertEquals(expected.size(),result.size());
        assertEquals(expected,result);

    }
    @Test
    void addDeliveryAddressToRestaurantWorksCorrectly() {
        // Given
        Restaurant someRestaurant = someRestaurant1();
        DeliveryAddress someDeliveryAddress = someDeliveryAddress1();
        doNothing().when(deliveryAddressDAO).addDeliveryAddressToRestaurant(any(DeliveryAddress.class),any(Restaurant.class));
        // When
        deliveryAddressService.addDeliveryAddressToRestaurant(someDeliveryAddress,someRestaurant);
        // Then
        verify(deliveryAddressDAO,times(1)).addDeliveryAddressToRestaurant(any(DeliveryAddress.class),any(Restaurant.class));
    }
    @Test
    void getPaginatedRestaurantDeliveryAddressesWorksCorrectly() {
        // Given
        Pageable somePageable = PageRequest.of(4, 12);
        Page<DeliveryAddress> somePagedDeliveries = new PageImpl<>(someAddresses());
        Page<DeliveryAddressDTO> somePagedDeliveriesDTO = new PageImpl<>(someAddressesDTO());
        when(deliveryAddressDAO.getPaginatedRestaurantDeliveryAddresses(anyInt(), any(Pageable.class)))
            .thenReturn(somePagedDeliveries);
        when(deliveryAddressMapper.mapToDTO(any(DeliveryAddress.class))).thenReturn(someDeliveryAddressDTO1());

        // When
        Page<DeliveryAddressDTO> result = deliveryAddressService.getPaginatedRestaurantDeliveryAddresses(5, somePageable);

        // Then
        verify(deliveryAddressMapper, times(somePagedDeliveries.getSize())).mapToDTO(any(DeliveryAddress.class));
        verify(deliveryAddressDAO, times(1)).getPaginatedRestaurantDeliveryAddresses(anyInt(),any(Pageable.class));
        assertEquals(somePagedDeliveriesDTO.getSize(),result.getSize());
    }

    @Test
    void countDeliveryAddressesForRestaurantWorksCorrectly() {
        // Given
        Restaurant someRestaurant = someRestaurant2();
        Integer expected = 67;
        when(deliveryAddressDAO.countDeliveryAddressesForRestaurant(any(Restaurant.class))).thenReturn(expected);
        // When
        Integer result = deliveryAddressService.countDeliveryAddressesForRestaurant(someRestaurant);

        // Then
        assertEquals(expected, result);
    }
    @Test
    void deleteByIdWorksCorrectly() {
        doNothing().when(deliveryAddressDAO).deleteById(anyInt());
        // When
        deliveryAddressService.deleteById(54);
        // Then
        verify(deliveryAddressDAO,times(1)).deleteById(anyInt());
    }
    @Test
    void findRestaurantsWithAddressWorksCorrectly() {
        // Given
        Address someAddress = AccountUtils.someAddress();
        List<Restaurant> expected = someRestaurants();
        when(deliveryAddressDAO.findRestaurantsWithAddress(anyString(), anyString(), anyString())).thenReturn(expected);
        // When

        List<Restaurant> result = deliveryAddressService.findRestaurantsWithAddress(someAddress);
        // Then
        assertEquals(expected, result);
    }
}
