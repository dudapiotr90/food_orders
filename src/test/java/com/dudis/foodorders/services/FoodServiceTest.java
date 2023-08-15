package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.mappers.FoodMapper;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.services.dao.FoodDAO;
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

import static com.dudis.foodorders.utils.FoodUtils.*;
import static com.dudis.foodorders.utils.MenuUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @InjectMocks
    private FoodService foodService;
    @Mock
    private FoodDAO foodDAO;
    @Mock
    private FoodMapper foodMapper;

    @Test
    void addFoodToMenuWorksCorrectly() {
       // Given
        Food someFood = someFood1();
        Menu someMenu = someMenu1();
        String someImagePath = "image/path/to/add";
        doNothing().when(foodDAO).saveFood(any(Food.class),any(Menu.class),anyString());
       // When
        foodService.addFoodToMenu(someFood, someMenu, someImagePath);

       // Then
        verify(foodDAO,times(1)).saveFood(any(Food.class),any(Menu.class),anyString());
    }

    @Test
    void updateMenuPositionWorksCorrectly() {
        // Given
        Food someFood = someFood1();
        String someImagePath = "image/path/to/update";
        String expected = "new/image/path/after/update";
        when(foodDAO.updateFood(any(Food.class), anyString())).thenReturn(expected);
        // When
        String result = foodService.updateMenuPosition(someFood, someImagePath);

        // Then
        verify(foodDAO,times(1)).updateFood(any(Food.class),anyString());
        assertEquals(expected,result);
    }

    @Test
    void deleteFoodMenuWorksCorrectly() {
        // Given
        Integer someFoodId = someFood1().getFoodId();
        String expectedDeletedPath = "new/image/path/after/update";
        when(foodDAO.deleteFood(anyInt())).thenReturn(expectedDeletedPath);
        // When
        String result = foodService.deleteFood(someFoodId);

        // Then
        verify(foodDAO,times(1)).deleteFood(anyInt());
        assertEquals(expectedDeletedPath,result);
    }

    @Test
    void getPaginatedFoodsWorksCorrectly() {
        // Given
        Pageable somePageable = PageRequest.of(4, 12);
        Page<Food> somePagedFoods = new PageImpl<>(someFoodsList());
        Page<FoodDTO> somePagedFoodsDTO = new PageImpl<>(someFoodsListDTO());
        when(foodDAO.getPaginatedFoods(anyInt(), any(Pageable.class)))
            .thenReturn(somePagedFoods);
        when(foodMapper.mapToDTO(any(Food.class))).thenReturn(someFoodDTO1());

        // When
        Page<FoodDTO> result = foodService.getPaginatedFoods(5, somePageable);

        // Then
        verify(foodMapper, times(somePagedFoods.getSize())).mapToDTO(any(Food.class));
        verify(foodDAO, times(1)).getPaginatedFoods(anyInt(),any(Pageable.class));
        assertEquals(somePagedFoodsDTO.getSize(),result.getSize());
    }

    @Test
    void findMenuByFoodWorksCorrectly() {
        // Given
        Food someFood = someFood1();
        Menu expected = someMenu1();
        when(foodDAO.findMenuByFood(any(Food.class))).thenReturn(expected);
        // When
        Menu result = foodService.findMenuByFood(someFood);

        // Then
        verify(foodDAO,times(1)).findMenuByFood(any(Food.class));
        assertEquals(expected,result);
    }

    @Test
    void findAllFoodWhereMenuWorksCorrectly() {
        // Given
        Menu someMenu = someMenu1();
        List<Food> expected = someFoodsList();
        when(foodDAO.findAllFoodWhereMenu(any(Menu.class))).thenReturn(expected);
        // When
        List<Food> result = foodService.findAllFoodWhereMenu(someMenu);

        // Then
        verify(foodDAO,times(1)).findAllFoodWhereMenu(any(Menu.class));
        assertEquals(expected,result);
    }
}