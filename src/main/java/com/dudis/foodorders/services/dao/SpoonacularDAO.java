package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;
import com.dudis.foodorders.infrastructure.spoonacular.Meal;
import com.dudis.foodorders.infrastructure.spoonacular.MealMap;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SpoonacularDAO {

    String youtubeLink = "https://www.youtube.com/watch?v=";

    String getRandomTrivia();

    String getRandomJoke();

    List<SpoonacularVideoDataDTO> searchForVideoRecipe(String query, String mealType, String cuisine, String diet, BigDecimal videoLength, int resultsToSkip);

    Map<String, MealMap> getMealPlanForDay(String timeFrame, BigDecimal caloriesPerDay, String diet, String exclude);

    Map<String, MealMap> getMealPlanForWeek(String timeFrame, BigDecimal caloriesPerDay, String s, String exclude) throws JsonProcessingException;
}
