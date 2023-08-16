package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.infrastructure.database.mappers.FoodEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.FoodJpaRepository;
import jakarta.persistence.EntityNotFoundException;
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

import static com.dudis.foodorders.utils.FoodUtils.*;
import static com.dudis.foodorders.utils.MenuUtils.someMenu1;
import static com.dudis.foodorders.utils.MenuUtils.someMenuEntity1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodRepositoryTest {

    @InjectMocks
    private FoodRepository foodRepository;

    @Mock
    private FoodJpaRepository foodJpaRepository;
    @Mock
    private FoodEntityMapper foodEntityMapper;
    @Mock
    private MenuEntityMapper menuEntityMapper;

    @Test
    void saveFoodWorksCorrectly() {
        // Given
        Food someFood = someFood1();
        Menu someMenu = someMenu1();
        String someImagePath = "some/image/path";
        when(menuEntityMapper.mapToEntity(any(Menu.class))).thenReturn(someMenuEntity1());
        when(foodEntityMapper.mapToEntity(any(Food.class))).thenReturn(someFoodEntity1());
        when(foodJpaRepository.saveAndFlush(someFoodEntity1())).thenReturn(someFoodEntity1());

        // When, Then
        foodRepository.saveFood(someFood, someMenu, someImagePath);
        verify(menuEntityMapper, times(1)).mapToEntity(any(Menu.class));
        verify(foodEntityMapper, times(1)).mapToEntity(any(Food.class));
        verify(foodJpaRepository, times(1)).saveAndFlush(any(FoodEntity.class));
    }
    @Test
    void updateFoodWorksCorrectly() {
        // Given
        Food someFood = someFood1();
        String someImagePath = "some/image/path";
        when(foodJpaRepository.findById(anyInt()))
            .thenReturn(Optional.of(someFoodEntity1()))
            .thenReturn(Optional.empty());
        when(foodJpaRepository.saveAndFlush(someFoodEntity1())).thenReturn(someFoodEntity1());
        String expectedMessage = "FoodEntity with id";

        // Given
        String result = foodRepository.updateFood(someFood, someImagePath);
        Throwable exception = assertThrows(EntityNotFoundException.class,
            () -> foodRepository.updateFood(someFood, someImagePath));
        // Then
        assertEquals(someFood.getFoodImagePath(),result);
        assertTrue(exception.getMessage().contains(expectedMessage));

    }
    @Test
    void deleteFoodWorksCorrectly() {
        // Given
        String expected = someFoodEntity1().getFoodImagePath();
        when(foodJpaRepository.findById(anyInt()))
            .thenReturn(Optional.of(someFoodEntity1()))
            .thenReturn(Optional.empty());
        doNothing().when(foodJpaRepository).deleteById(anyInt());
        String expectedMessage = "FoodEntity with id";

        // When
        String result = foodRepository.deleteFood(3453);
        Throwable exception = assertThrows(EntityNotFoundException.class,
            () -> foodRepository.deleteFood(123));
        // Then
        assertTrue(exception.getMessage().contains(expectedMessage));
        assertEquals(expected,result);
        verify(foodJpaRepository,times(1)).deleteById(anyInt());
    }
    @Test
    void getPaginatedFoodsWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(3, 7);
        Integer someId = 123;
        Page<FoodEntity> pagedFoodEntities = new PageImpl<>(someFoodEntities());
        Page<Food> expected = new PageImpl<>(someFoodsList());
        when(foodJpaRepository.findByMenuId(anyInt(), any(Pageable.class)))
            .thenReturn(pagedFoodEntities);
        when(foodEntityMapper.mapFromEntity(any(FoodEntity.class)))
            .thenReturn(someFood1(), someFood2(), someFood3());

        // When
        Page<Food> result = foodRepository.getPaginatedFoods(someId, pageable);
        // Then
        assertEquals(expected,result);
    }

    @Test
    void findMenuByFoodWorksCorrectly() {
        // Given
        Food someFood = someFood1();
        Menu expected = someMenu1();
        when(foodEntityMapper.mapToEntity(any(Food.class))).thenReturn(someFoodEntity1());
        when(foodJpaRepository.findMenuByFoodId(anyInt()))
            .thenReturn(someMenuEntity1());
        when(menuEntityMapper.mapFromEntity(any(MenuEntity.class)))
            .thenReturn(expected);

        // Given
        Menu result = foodRepository.findMenuByFood(someFood);

        // Then
        assertEquals(expected,result);
    }
    @Test
    void findAllFoodWhereMenuWorksCorrectly() {
        // Given
        List<Food> expected = someFoodsList();
        Menu someMenu = someMenu1();
        when(menuEntityMapper.mapToEntity(any(Menu.class))).thenReturn(someMenuEntity1());
        when(foodJpaRepository.findByMenuId(anyInt()))
            .thenReturn(someFoodEntities());
        when(foodEntityMapper.mapFromEntity(any(FoodEntity.class)))
            .thenReturn(someFood1(), someFood2(), someFood3());

        // When
        List<Food> result = foodRepository.findAllFoodWhereMenu(someMenu);
        // Then
        assertEquals(expected,result);
        verify(foodEntityMapper, times(expected.size())).mapFromEntity(any(FoodEntity.class));
        verify(menuEntityMapper, times(1)).mapToEntity(any(Menu.class));
        verify(foodJpaRepository, times(1)).findByMenuId(anyInt());
    }
}