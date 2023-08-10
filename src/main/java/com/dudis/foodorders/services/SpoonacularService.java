package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.SearchSpoonacularParametersDTO;
import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;
import com.dudis.foodorders.infrastructure.spoonacular.Diet;
import com.dudis.foodorders.infrastructure.spoonacular.MealMap;
import com.dudis.foodorders.services.dao.SpoonacularDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@AllArgsConstructor
public class SpoonacularService {

    private final SpoonacularDAO spoonacularDAO;

    public String getRandomFoodTrivia() {
        return spoonacularDAO.getRandomTrivia();
    }

    public String getRandomJoke() {
        return spoonacularDAO.getRandomJoke();
    }

    public SpoonacularVideoDataDTO searchForRandomVideoRecipe(SearchSpoonacularParametersDTO parameters) {
        String query = parameters.getQuery();
        String diet = parameters.getDiet();
        String[] cuisineTable = parameters.getCuisine();
        String mealType = parameters.getMealType();
        BigDecimal videoLength = parameters.getMaxVideoLengthInSeconds();
        int resultsToSkip = new Random().nextInt(1, 5);

        String cuisine = Arrays.stream(cuisineTable)
            .reduce((prev, next) -> prev + "," + next)
            .orElse("");

        List<SpoonacularVideoDataDTO> spoonacularVideoDataDTOs = spoonacularDAO.searchForVideoRecipe(query, mealType, cuisine, diet, videoLength,
            resultsToSkip);

        return spoonacularVideoDataDTOs.size() > 0 ?
            spoonacularVideoDataDTOs.get(new Random().nextInt(spoonacularVideoDataDTOs.size())) :
            SpoonacularVideoDataDTO.builder()
                .title("No results found!").build();

    }

    public Map<String, MealMap> getMealPlan(String timeFrame, BigDecimal caloriesPerDay, Diet diet, String exclude) throws JsonProcessingException {
        if ("week".equalsIgnoreCase(timeFrame)) {
            return spoonacularDAO.getMealPlanForWeek(
                "week",
                caloriesPerDay,
                Objects.isNull(diet) ? null : diet.getDiet(),
                exclude);
        } else {
            return spoonacularDAO.getMealPlanForDay(
                "day",
                caloriesPerDay,
                Objects.isNull(diet) ? null : diet.getDiet(),
                exclude);
        }
    }
}
