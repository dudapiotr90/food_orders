package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.utils.FoodUtils;
import com.dudis.foodorders.utils.MenuUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;
    @Mock
    private FoodService foodService;

    @Test
    void menuContainsFoodWorksCorrectly() {
        // Given
        Menu someMenu1 = MenuUtils.someMenu1();
        Menu someMenu2 = MenuUtils.someMenu2();
        Food someFood = FoodUtils.someFood1();
        Mockito.when(foodService.findAllFoodWhereMenu(someMenu1)).thenReturn(FoodUtils.someFoodsList());
        Mockito.when(foodService.findAllFoodWhereMenu(someMenu2)).thenReturn(List.of());

        // When
        boolean result1 = menuService.menuContainsFood(someFood, someMenu1);
        boolean result2 = menuService.menuContainsFood(someFood, someMenu2);

        // Then
        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }
}