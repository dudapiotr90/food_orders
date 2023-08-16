package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OwnerEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.RestaurantJpaRepository;
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

import static com.dudis.foodorders.utils.MenuUtils.someMenu1;
import static com.dudis.foodorders.utils.MenuUtils.someMenuEntity1;
import static com.dudis.foodorders.utils.OwnerUtils.someOwner1;
import static com.dudis.foodorders.utils.OwnerUtils.someOwnerEntity1;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantRepositoryTest {

    @InjectMocks
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantJpaRepository restaurantJpaRepository;
    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;
    @Mock
    private MenuEntityMapper menuEntityMapper;
    @Mock
    private OwnerEntityMapper ownerEntityMapper;

    @Test
    void findRestaurantsWhereOwnerIdWorksCorrectly() {
        // Given
        when(restaurantJpaRepository.findByOwnerId(anyInt()))
            .thenReturn(someRestaurantEntities());
        when(restaurantEntityMapper.mapFromEntity(any(RestaurantEntity.class)))
            .thenReturn(someRestaurant1(), someRestaurant2(), someRestaurant3());
        List<Restaurant> expected = someRestaurants();

        // Given, Then
        List<Restaurant> result = restaurantRepository.findRestaurantsWhereOwnerId(423);
        assertEquals(expected,result);
    }
    @Test
    void addLocalWorksCorrectly() {
        // Given
        Restaurant someRestaurant = someRestaurant1();
        when(restaurantEntityMapper.mapToEntity(any(Restaurant.class)))
            .thenReturn(someRestaurantEntity1());
        when(restaurantJpaRepository.save(someRestaurantEntity1())).thenReturn(someRestaurantEntity1());
        // When, Then
        restaurantRepository.addLocal(someRestaurant);
        verify(restaurantEntityMapper, times(1)).mapToEntity(any(Restaurant.class));
        verify(restaurantJpaRepository, times(1)).save(any(RestaurantEntity.class));
    }
    @Test
    void findProcessingRestaurantWorksCorrectly() {
        // Given
        when(restaurantJpaRepository.findById(anyInt()))
            .thenReturn(Optional.of(someRestaurantEntity1()))
            .thenReturn(Optional.empty());
        when(restaurantEntityMapper.mapFromEntity(any(RestaurantEntity.class)))
            .thenReturn(someRestaurant1());
        Optional<Restaurant> expected = Optional.of(someRestaurant1());

        // When, Then
        Optional<Restaurant> result = restaurantRepository.findProcessingRestaurant(3232);
        restaurantRepository.findProcessingRestaurant(12);
        assertEquals(expected,result);
        verify(restaurantJpaRepository, times(2)).findById(anyInt());
        verify(restaurantEntityMapper, times(1)).mapFromEntity(any(RestaurantEntity.class));
    }
    @Test
    void getMenuWorksCorrectly() {
        // Given
        when(restaurantJpaRepository.findMenuByRestaurantId(anyInt()))
            .thenReturn(Optional.of(someMenuEntity1()))
            .thenReturn(Optional.empty());
        when(menuEntityMapper.mapFromEntity(any(MenuEntity.class)))
            .thenReturn(someMenu1());
        Optional<Menu> expected = Optional.of(someMenu1());

        // When, Then
        Optional<Menu> result = restaurantRepository.getMenu(3232);
        restaurantRepository.getMenu(12);
        assertEquals(expected,result);
        verify(restaurantJpaRepository, times(2)).findMenuByRestaurantId(anyInt());
        verify(menuEntityMapper, times(1)).mapFromEntity(any(MenuEntity.class));
    }
//    @Test
//    void getPaginatedMenuWorksCorrectly() {
//        // Given
//
//    }
    @Test
    void findRestaurantByMenuWorksCorrectly() {
        // Given
        Restaurant expected = someRestaurant1();
        Menu someMenu = someMenu1();
        when(menuEntityMapper.mapToEntity(someMenu))
            .thenReturn(someMenuEntity1());
        when(restaurantJpaRepository.findByMenu(someMenuEntity1()))
            .thenReturn(someRestaurantEntity1());
        when(restaurantEntityMapper.mapFromEntity(someRestaurantEntity1()))
            .thenReturn(expected);

        // When, Then
        Restaurant result = restaurantRepository.findRestaurantByMenu(someMenu);
        assertEquals(expected, result);
    }
    @Test
    void findOwnerByRestaurantWorksCorrectly() {
        // Given
        Restaurant someRestaurant = someRestaurant1();
        when(restaurantJpaRepository.findOwnerByRestaurantId(someRestaurant.getRestaurantId()))
            .thenReturn(Optional.of(someOwnerEntity1()))
            .thenReturn(Optional.empty());
        when(ownerEntityMapper.mapFromEntity(any(OwnerEntity.class)))
            .thenReturn(someOwner1());
        Optional<Owner> expected = Optional.of(someOwner1());

        // When, Then
        Optional<Owner> result = restaurantRepository.findOwnerByRestaurant(someRestaurant);
        assertEquals(expected,result);
    }
    @Test
    void findAllRestaurantsWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(2, 6);
        Page<RestaurantEntity> restaurantEntities = new PageImpl<>(someRestaurantEntities());
        Page<Restaurant> expected = new PageImpl<>(someRestaurants());
        when(restaurantJpaRepository.findAll(any(Pageable.class))).thenReturn(restaurantEntities);
        when(restaurantEntityMapper.mapFromEntity(any(RestaurantEntity.class)))
            .thenReturn(someRestaurant1(),someRestaurant2(),someRestaurant3());

        // When
        Page<Restaurant> result = restaurantRepository.findAllRestaurants(pageable);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void findAllRestaurantsByCityWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(2, 6);
        String someCity = "someCity";
        Page<Object[]> restaurantDetails = new PageImpl<>(someRestaurantsAsObjects());
        when(restaurantJpaRepository.findAllRestaurantsByCity(someCity, pageable))
            .thenReturn(restaurantDetails);
        when(restaurantEntityMapper.buildRestaurantFromObject(any(Object[].class)))
            .thenReturn(someRestaurant1(),someRestaurant2(),someRestaurant3());
        Page<Restaurant> expected = new PageImpl<>(someRestaurants());

        // When, Then
        Page<Restaurant> result = restaurantRepository.findAllRestaurantsByCity(someCity, pageable);
        assertEquals(expected,result);
    }
    @Test
    void findAllRestaurantsByFullAddressWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(2, 6);
        String someCity = "someCity";
        String somePostalCode = "00-000";
        String someStreet = "someStreet";
        Page<Object[]> restaurantDetails = new PageImpl<>(someRestaurantsAsObjects());
        when(restaurantJpaRepository.findAllRestaurantsByFullAddress(someCity,somePostalCode,someStreet, pageable))
            .thenReturn(restaurantDetails);
        when(restaurantEntityMapper.buildRestaurantFromObject(any(Object[].class)))
            .thenReturn(someRestaurant1(),someRestaurant2(),someRestaurant3());
        Page<Restaurant> expected = new PageImpl<>(someRestaurants());

        // When, Then
        Page<Restaurant> result = restaurantRepository.findAllRestaurantsByFullAddress(someCity,somePostalCode,someStreet, pageable);
        assertEquals(expected,result);
    }

}