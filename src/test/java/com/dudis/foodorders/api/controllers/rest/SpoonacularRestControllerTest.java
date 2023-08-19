package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.infrastructure.spoonacular.Diet;
import com.dudis.foodorders.infrastructure.spoonacular.MealMap;
import com.dudis.foodorders.services.SpoonacularService;
import com.dudis.foodorders.utils.SpoonacularUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static com.dudis.foodorders.api.controllers.rest.DeveloperEndpoint.DEV;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SpoonacularRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class SpoonacularRestControllerTest {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    @MockBean
    private SpoonacularService spoonacularService;

    public static Stream<Arguments> genMealPlanWorksCorrectly() {
        return Stream.of(
            Arguments.of("week", new BigDecimal("2000"), Diet.VEGETARIAN, "cheese"),
            Arguments.of("day", new BigDecimal("2500"), Diet.KETOGENIC, "diary,eggs"),
            Arguments.of("week", new BigDecimal("3500"), Diet.VEGAN, "tomatoes"),
            Arguments.of("day", new BigDecimal("1500"), Diet.GLUTEN_FREE, "brocolly")
        );
    }

    @ParameterizedTest
    @MethodSource
    void genMealPlanWorksCorrectly(
        String timeFrame,
        BigDecimal caloriesPerDay,
        Diet diet,
        String exclude
    ) throws Exception {
        // Given
        Map<String, MealMap> someMealPlanForDay = SpoonacularUtils.someMealPlanForDay();
        Map<String, MealMap> someMealPlanForWeek = SpoonacularUtils.someMealPlanForWeek();

        when(spoonacularService.getMealPlan("day", caloriesPerDay, diet, exclude))
            .thenReturn(someMealPlanForDay);
        when(spoonacularService.getMealPlan("week", caloriesPerDay, diet, exclude))
            .thenReturn(someMealPlanForWeek);

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get(DEV + SpoonacularRestController.MEAL_PLAN)
            .param("timeFrame", timeFrame)
            .param("exclude", exclude)
            .param("caloriesPerDay", objectMapper.writeValueAsString(caloriesPerDay))
            .param("diet", diet.name()));

        if ("week".equalsIgnoreCase(timeFrame)) {
            perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Day1.meals[2].title", is("Meal 2")))
                .andExpect(jsonPath("$.Day5.meals[0].servings", is(2)))
                .andExpect(jsonPath("$.Day1.meals[1].id", is(21)))
                .andExpect(jsonPath("$.Day2.meals[0].title", is("Meal 3")))
                .andExpect(jsonPath("$.Day6.meals[0].servings", is(2)))
                .andExpect(jsonPath("$.Day2.meals[2].id", is(66)))
                .andExpect(jsonPath("$.Day3.meals[0].title", is("Meal 3")))
                .andExpect(jsonPath("$.Day4.meals[2].servings", is(1)))
                .andExpect(jsonPath("$.Day3.meals[0].id", is(3)))
                .andExpect(jsonPath("$.Day5.meals[1].readyInMinutes", is(45)))
                .andExpect(jsonPath("$.Day1.nutrients.calories", is(1850)))
                .andExpect(jsonPath("$.Day6.nutrients.protein", is(145)))
                .andExpect(jsonPath("$.Day4.meals[2].readyInMinutes", is(30)))
                .andExpect(jsonPath("$.Day4.nutrients.fat", is(65)))
                .andExpect(jsonPath("$.Day7.nutrients.carbohydrates", is(44)));
        } else {
            perform
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Day1.meals[0].title", is("Meal 3")))
                .andExpect(jsonPath("$.Day1.meals[0].servings", is(2)))
                .andExpect(jsonPath("$.Day1.meals[0].id", is(3)))
                .andExpect(jsonPath("$.Day1.meals[0].readyInMinutes", is(60)))
                .andExpect(jsonPath("$.Day1.meals[0].sourceUrl", is("some recipe url 3")))
                .andExpect(jsonPath("$.Day1.meals[1].title", is("Meal 1")))
                .andExpect(jsonPath("$.Day1.meals[1].servings", is(10)))
                .andExpect(jsonPath("$.Day1.meals[1].id", is(21)))
                .andExpect(jsonPath("$.Day1.meals[1].readyInMinutes", is(45)))
                .andExpect(jsonPath("$.Day1.meals[1].sourceUrl", is("some recipe url 1")))
                .andExpect(jsonPath("$.Day1.meals[2].title", is("Meal 2")))
                .andExpect(jsonPath("$.Day1.meals[2].servings", is(1)))
                .andExpect(jsonPath("$.Day1.meals[2].id", is(66)))
                .andExpect(jsonPath("$.Day1.meals[2].readyInMinutes", is(30)))
                .andExpect(jsonPath("$.Day1.meals[2].sourceUrl", is("some recipe url 2")))
                .andExpect(jsonPath("$.Day1.nutrients.calories", is(1850)))
                .andExpect(jsonPath("$.Day1.nutrients.protein", is(145)))
                .andExpect(jsonPath("$.Day1.nutrients.fat", is(65)))
                .andExpect(jsonPath("$.Day1.nutrients.carbohydrates", is(44)));
        }
    }
}