package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.SearchSpoonacularParametersDTO;
import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;
import com.dudis.foodorders.infrastructure.spoonacular.Meal;
import com.dudis.foodorders.infrastructure.spoonacular.MealMap;
import com.dudis.foodorders.infrastructure.spoonacular.Nutrients;

import java.math.BigDecimal;
import java.util.*;

public class SpoonacularUtils {


    public static SearchSpoonacularParametersDTO someParameters() {
        String[] cuisines = {"American", "Korean"};
        return SearchSpoonacularParametersDTO.builder()
            .query("pizza")
            .cuisine(cuisines)
            .diet("vegetarian")
            .mealType("beverage")
            .maxVideoLengthInSeconds(new BigDecimal(148))
            .build();
    }
    public static SearchSpoonacularParametersDTO someParametersForNoResults() {
        return SearchSpoonacularParametersDTO.builder()
            .query("pizza")
            .diet("vegetarian")
            .mealType("non existing")
            .maxVideoLengthInSeconds(BigDecimal.ZERO)
            .build();
    }

    public static List<SpoonacularVideoDataDTO> someVideosData() {
        return List.of(someVideoData1(), someVideoData2(), someVideoData3());
    }

    public static SpoonacularVideoDataDTO someVideoData1() {
        return SpoonacularVideoDataDTO.builder()
            .title("some title 1")
            .videoLength(671)
            .views(7418)
            .dishPreview("some dish preview url 1")
            .youtubeVideoLink("some youtube video link 1")
            .build();
    }
    public static SpoonacularVideoDataDTO someVideoData2() {
        return SpoonacularVideoDataDTO.builder()
            .title("some title 2")
            .videoLength(99)
            .views(728)
            .dishPreview("some dish preview url 2")
            .youtubeVideoLink("some youtube video link 2")
            .build();
    }
    public static SpoonacularVideoDataDTO someVideoData3() {
        return SpoonacularVideoDataDTO.builder()
            .title("some title 3")
            .videoLength(173)
            .views(17438)
            .dishPreview("some dish preview url 3")
            .youtubeVideoLink("some youtube video link 3")
            .build();
    }

    public static Map<String, MealMap> someMealPlanForDay() {
        Map<String, MealMap> mealPlan = new LinkedHashMap<>();
        mealPlan.put("Day1", someMealMap1());
        return mealPlan;
    }

    public static MealMap someMealMap1() {
        return MealMap.builder()
            .meals(someMeals())
            .nutrients(someNutrients())
            .build();
    }

    public static Nutrients someNutrients() {
        return Nutrients.builder()
            .calories(BigDecimal.valueOf(1850))
            .protein(BigDecimal.valueOf(145))
            .fat(BigDecimal.valueOf(65))
            .carbohydrates(BigDecimal.valueOf(44))
            .build();
    }

    public static TreeSet<Meal> someMeals() {
        return new TreeSet<>(Set.of(someMeal1(),someMeal2(),someMeal3()));
    }

    public static Meal someMeal1() {
        return Meal.builder()
            .mealId(21)
            .title("Meal 1")
            .preparationTime(45)
            .servings(BigDecimal.TEN)
            .recipeUrl("some recipe url 1")
            .build();
    }

    public static Meal someMeal2() {
        return Meal.builder()
            .mealId(66)
            .title("Meal 2")
            .preparationTime(30)
            .servings(BigDecimal.ONE)
            .recipeUrl("some recipe url 2")
            .build();
    }

    public static Meal someMeal3() {
        return Meal.builder()
            .mealId(3)
            .title("Meal 3")
            .preparationTime(60)
            .servings(BigDecimal.valueOf(2))
            .recipeUrl("some recipe url 3")
            .build();
    }

    public static Map<String, MealMap> someMealPlanForWeek() {
        Map<String, MealMap> mealPlan = new LinkedHashMap<>();
        mealPlan.put("Day1", someMealMap1());
        mealPlan.put("Day2", someMealMap1());
        mealPlan.put("Day3", someMealMap1());
        mealPlan.put("Day4", someMealMap1());
        mealPlan.put("Day5", someMealMap1());
        mealPlan.put("Day6", someMealMap1());
        mealPlan.put("Day7", someMealMap1());
        return mealPlan;
    }
}
