package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.SearchSpoonacularParametersDTO;
import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;
import com.dudis.foodorders.infrastructure.spoonacular.Diet;
import com.dudis.foodorders.infrastructure.spoonacular.MealMap;
import com.dudis.foodorders.services.dao.SpoonacularDAO;
import com.dudis.foodorders.utils.SpoonacularUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.dudis.foodorders.utils.SpoonacularUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpoonacularServiceTest {

    @InjectMocks
    private SpoonacularService spoonacularService;

    @Mock
    private SpoonacularDAO spoonacularDAO;


    @Test
    void getRandomFoodTriviaWorksCorrectly() {
        // Given
        String expected = "some random Trivia";
        when(spoonacularDAO.getRandomTrivia()).thenReturn(expected);

        // When
        String result = spoonacularService.getRandomFoodTrivia();

        // Then
        assertEquals(expected, result);
    }
    @Test
    void getRandomJokeWorksCorrectly() {
        // Given
        String expected = "some random joke";
        when(spoonacularDAO.getRandomJoke()).thenReturn(expected);

        // When
        String result = spoonacularService.getRandomJoke();

        // Then
        assertEquals(expected, result);
    }
    @Test
    void searchForRandomVideoRecipeWorksCorrectly() {
        // Given
        SearchSpoonacularParametersDTO someParameters1 = someParameters();
        SearchSpoonacularParametersDTO someParameters2 = someParametersForNoResults();
        List<SpoonacularVideoDataDTO> someList = someVideosData();
        String expectedTitle = "No results found!";
        when(spoonacularDAO.searchForVideoRecipe(anyString(), anyString(), anyString(), anyString(), any(BigDecimal.class), anyInt()))
            .thenReturn(someList).thenReturn(List.of());

        // When
        SpoonacularVideoDataDTO result1 = spoonacularService.searchForRandomVideoRecipe(someParameters1);
        SpoonacularVideoDataDTO result2 = spoonacularService.searchForRandomVideoRecipe(someParameters2);

        assertTrue(someList.contains(result1));
        assertEquals(expectedTitle,result2.getTitle());
    }

    @Test
    void getMealPlanWorksCorrectly() throws JsonProcessingException {
        // Given
        Map<String, MealMap> expected1 = SpoonacularUtils.someMealPlanForDay();
        Map<String, MealMap> expected2 = SpoonacularUtils.someMealPlanForWeek();
        when(spoonacularDAO.getMealPlanForDay("day",null, null, null))
            .thenReturn(expected1);
        when(spoonacularDAO.getMealPlanForWeek("week", null,null, null))
            .thenReturn(expected2);

        // When
        Map<String, MealMap> result1 = spoonacularService.getMealPlan(null, null, null, null);
        Map<String, MealMap> result2 = spoonacularService.getMealPlan("week", null, null, null);

        // Then
        assertEquals(expected1,result1);
        assertEquals(expected2,result2);
    }
}