package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryAddressEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import com.dudis.foodorders.infrastructure.database.mappers.DeliveryAddressEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.DeliveryAddressJpaRepository;
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

import java.util.List;

import static com.dudis.foodorders.utils.DeliveryAddressUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryAddressRepositoryTest {

    @InjectMocks
    private DeliveryAddressRepository deliveryAddressRepository;

    @Mock
    private DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    @Mock
    private DeliveryAddressEntityMapper deliveryAddressEntityMapper;
    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;

    @Test
    void getRestaurantDeliveryAddressesWorksCorrectly() {
        // Given
        List<DeliveryAddress> expected = someAddresses();
        when(deliveryAddressJpaRepository.findDeliveryAddressesByRestaurantId(anyInt()))
            .thenReturn(someDeliveryAddressEntities());
        when(deliveryAddressEntityMapper.mapFromEntity(any(DeliveryAddressEntity.class)))
            .thenReturn(someDeliveryAddress1(), someDeliveryAddress2(), someDeliveryAddress3());
        // When
        List<DeliveryAddress> result = deliveryAddressRepository.getRestaurantDeliveryAddresses(342);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void addDeliveryAddressToRestaurantWorksCorrectly() {
        // Given
        DeliveryAddress someAddress = someDeliveryAddress1();
        Restaurant someRestaurant = someRestaurant1();
        when(deliveryAddressEntityMapper.mapToEntity(any(DeliveryAddress.class)))
            .thenReturn(someDeliveryAddressEntity1());
        when(restaurantEntityMapper.mapToEntity(any(Restaurant.class)))
            .thenReturn(someRestaurantEntity1());
        when(deliveryAddressJpaRepository.save(someDeliveryAddressEntity1()))
            .thenReturn(someDeliveryAddressEntity1());

        // When
        deliveryAddressRepository.addDeliveryAddressToRestaurant(someAddress, someRestaurant);
        // Then
        verify(deliveryAddressEntityMapper, times(1)).mapToEntity(any(DeliveryAddress.class));
        verify(restaurantEntityMapper, times(1)).mapToEntity(any(Restaurant.class));
        verify(deliveryAddressJpaRepository, times(1)).save(any(DeliveryAddressEntity.class));
    }
    @Test
    void getPaginatedRestaurantDeliveryAddressesWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(2, 6);
        Page<DeliveryAddressEntity> deliveryAddressEntities = new PageImpl<>(someDeliveryAddressEntities());
        Page<DeliveryAddress> expected = new PageImpl<>(someAddresses());
        when(deliveryAddressJpaRepository.findPaginatedDeliveryAddressesByRestaurantId(anyInt(),any(Pageable.class)))
            .thenReturn(deliveryAddressEntities);
        when(deliveryAddressEntityMapper.mapFromEntity(any(DeliveryAddressEntity.class)))
            .thenReturn(someDeliveryAddress1(),someDeliveryAddress2(),someDeliveryAddress3());

        // When
        Page<DeliveryAddress> result = deliveryAddressRepository.getPaginatedRestaurantDeliveryAddresses(123,pageable);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void countDeliveryAddressesForRestaurantWorksCorrectly() {
        // Given
        Integer expected = 65;
        Restaurant someRestaurant = someRestaurant1();
        when(restaurantEntityMapper.mapToEntity(any(Restaurant.class))).thenReturn(someRestaurantEntity1());
        when(deliveryAddressJpaRepository.countByRestaurant(any(RestaurantEntity.class)))
            .thenReturn(expected);

        // When
        Integer result = deliveryAddressRepository.countDeliveryAddressesForRestaurant(someRestaurant);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void deleteByIdWorksCorrectly() {
        // Given
        Integer someId = 2314;
        doNothing().when(deliveryAddressJpaRepository).deleteById(anyInt());

        // When, Then
        deliveryAddressRepository.deleteById(someId);
        verify(deliveryAddressJpaRepository,times(1)).deleteById(anyInt());
    }
    @Test
    void findRestaurantsWithAddressWorksCorrectly() {
        // Given
        String someCity = "Kraków";
        String somePostalCode = "30-001";
        String someStreet = "Rynek";
        List<Restaurant> expected = someRestaurants();
        List<Object[]> someDetails = RestaurantUtils.someRestaurantsAsObjects();
        when(deliveryAddressJpaRepository.findRestaurantsWithAddress(anyString(), anyString(), anyString()))
            .thenReturn(someDetails);
        when(restaurantEntityMapper.buildRestaurantFromObject(any(Object[].class)))
            .thenReturn(someRestaurant1(), someRestaurant2(), someRestaurant3());

        // When
        List<Restaurant> result = deliveryAddressRepository.findRestaurantsWithAddress(someCity, somePostalCode, someStreet);
        // Then
        assertEquals(expected,result);
    }
}