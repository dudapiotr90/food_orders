package com.dudis.foodorders.infrastructure.spoonacular;

import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.spoonacular.api.MealPlanningApi;
import com.dudis.foodorders.infrastructure.spoonacular.api.MiscApi;
import com.dudis.foodorders.infrastructure.spoonacular.model.*;
import com.dudis.foodorders.services.dao.SpoonacularDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SpoonacularClientImpl implements SpoonacularDAO {

    private static final String VIDEO_EXCEPTION_MESSAGE = "No video found with given parameters[%s,%s,%s,%s,%s]";

    private final MiscApi miscApi;
    private final MealPlanningApi mealPlanningApi;

    private final ObjectMapper objectMapper;

    @Override
    public String getRandomTrivia() {
        GetRandomFoodTrivia200Response randomTriviaResponse = Optional.ofNullable(miscApi.getRandomFoodTrivia().block())
            .orElseThrow(() -> new NotFoundException("No food trivia available at the moment! Try again later"));

        return randomTriviaResponse.getText();
    }

    @Override
    public String getRandomJoke() {
        GetARandomFoodJoke200Response randomFoodJoke = Optional.ofNullable(miscApi.getARandomFoodJoke().block())
            .orElseThrow(() -> new NotFoundException("No food joke available at the moment! Try again later"));
        return randomFoodJoke.getText();
    }

    @Override
    public List<SpoonacularVideoDataDTO> searchForVideoRecipe(
        String query,
        String mealType,
        String cuisine,
        String diet,
        BigDecimal videoLength,
        int resultsToSkip
    ) {
        SearchFoodVideos200Response searchFoodVideos200ResponseMono = Optional.ofNullable(
                miscApi.searchFoodVideos(query, mealType, cuisine, diet, null, null, null, videoLength, resultsToSkip, 10).block())
            .orElseThrow(() -> new NotFoundException(
                String.format(VIDEO_EXCEPTION_MESSAGE, query, mealType, cuisine, diet, videoLength)
            ));
        return searchFoodVideos200ResponseMono.getVideos().stream()
            .map(video -> SpoonacularVideoDataDTO.builder()
                .title(video.getTitle())
                .videoLength(video.getLength())
                .views(video.getViews())
                .dishPreview(video.getThumbnail())
                .youtubeVideoLink(youtubeLink + video.getYouTubeId())
                .build())
            .toList();

    }

    @Override
    public Map<String, MealMap> getMealPlanForDay(String timeFrame, BigDecimal caloriesPerDay, String diet, String exclude) {
        GenerateMealPlan200Response plan = Optional.ofNullable(mealPlanningApi.generateMealPlan(timeFrame, caloriesPerDay, diet, exclude).block())
            .orElseThrow(() -> new NotFoundException("Cannot generate plan for given parameters"));

        Map<String, MealMap> mealPlan = new LinkedHashMap<>();
        Nutrients nutrients = buildNutrients(plan);
        TreeSet<Meal> meals = plan.getMeals().stream()
            .map(this::buildMeal)
            .collect(Collectors.toCollection(TreeSet::new));

        MealMap mealMap = buildMealMap(nutrients, meals);

        mealPlan.put("Day1", mealMap);
        return mealPlan;
    }

    @Override
    public Map<String, MealMap> getMealPlanForWeek(
        String timeFrame,
        BigDecimal caloriesPerDay,
        String diet,
        String exclude
    ) throws JsonProcessingException {
        String rawJson = mealPlanningApi.generateMealPlanWithResponseSpec(timeFrame, caloriesPerDay, diet, exclude)
            .bodyToMono(String.class).block();

        Map<String, MealMap> weeklyPlan = new LinkedHashMap<>();
        JsonNode jsonNode = objectMapper.readTree(rawJson);

        return mapJsonToMap(weeklyPlan, objectMapper, jsonNode);
    }

    private Map<String, MealMap> mapJsonToMap(Map<String, MealMap> weeklyPlan, ObjectMapper objectMapper, JsonNode jsonNode) {
        Iterator<Map.Entry<String, JsonNode>> daysIterator = jsonNode.get("week").fields();
        while (daysIterator.hasNext()) {
            Map.Entry<String, JsonNode> dayEntry = daysIterator.next();
            String day = dayEntry.getKey();
            JsonNode dayNode = dayEntry.getValue();
            MealMap dailyPlan = objectMapper.convertValue(dayNode, MealMap.class);
            weeklyPlan.put(day, dailyPlan);
        }
        return weeklyPlan;
    }

    private Meal buildMeal(GetSimilarRecipes200ResponseInner m) {
        return Meal.builder()
            .mealId(m.getId())
            .preparationTime(m.getReadyInMinutes())
            .servings(m.getServings())
            .title(m.getTitle())
            .recipeUrl(m.getSourceUrl())
            .build();
    }

    private Nutrients buildNutrients(GenerateMealPlan200Response plan) {
        return Nutrients.builder()
            .fat(plan.getNutrients().getFat())
            .carbohydrates(plan.getNutrients().getCarbohydrates())
            .protein(plan.getNutrients().getProtein())
            .calories(plan.getNutrients().getCalories())
            .build();
    }

    private MealMap buildMealMap(Nutrients nutrients, TreeSet<Meal> meals) {
        return MealMap.builder()
            .meals(meals)
            .nutrients(nutrients)
            .build();
    }
}
