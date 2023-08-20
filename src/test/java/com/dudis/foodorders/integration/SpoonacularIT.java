package com.dudis.foodorders.integration;

import com.dudis.foodorders.infrastructure.spoonacular.Diet;
import com.dudis.foodorders.infrastructure.spoonacular.MealMap;
import com.dudis.foodorders.integration.configuration.RestAssuredIntegrationTestBase;
import com.dudis.foodorders.integration.support.SpoonacularControllerTestSupport;
import com.dudis.foodorders.integration.support.WiremockTestSupport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SpoonacularIT
    extends RestAssuredIntegrationTestBase
    implements SpoonacularControllerTestSupport, WiremockTestSupport {

    @Test
    void randomMealPlanWorksCorrectly() {
        // Given
        logInto("developer","developer");

        String timeFrame = "day";
        String timeFrame2 = "week";
        BigDecimal targetCalories = new BigDecimal("2222");
        Diet diet = Diet.OVO_VEGETARIAN;
        String exclude = "";

        stubForMealPlan(wireMockServer,timeFrame,targetCalories, diet.getDiet(), exclude);
        stubForMealPlan(wireMockServer,timeFrame2,targetCalories, diet.getDiet(), exclude);

        // When
        Map<String, MealMap> mealPlanForDay = getMealPlan(timeFrame, targetCalories, diet, exclude);
        Map<String, MealMap> mealPlanForWeek = getMealPlan(timeFrame2, targetCalories, diet, exclude);

        // Then
        assertThat(mealPlanForDay).hasSize(1);
        assertThat(mealPlanForWeek).hasSize(7);
    }


}
