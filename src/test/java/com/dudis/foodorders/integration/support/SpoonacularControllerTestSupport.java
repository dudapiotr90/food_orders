package com.dudis.foodorders.integration.support;

import com.dudis.foodorders.api.controllers.rest.DeveloperEndpoint;
import com.dudis.foodorders.api.controllers.rest.SpoonacularRestController;
import com.dudis.foodorders.infrastructure.spoonacular.Diet;
import com.dudis.foodorders.infrastructure.spoonacular.MealMap;
import io.restassured.common.mapper.TypeRef;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public interface SpoonacularControllerTestSupport {
    RequestSpecification requestSpecification();

    default Map<String, MealMap> getMealPlan(
        String timeFrame,
        BigDecimal caloriesPerDay,
        Diet diet,
        String exclude
    ) {
        Map<String, String> params = Map.of(
            "timeFrame", timeFrame,
            "caloriesPerDay", caloriesPerDay.toString(),
            "diet", diet.name(),
            "exclude", exclude
        );
        return requestSpecification()
            .params(params)
            .get(DeveloperEndpoint.DEV+ SpoonacularRestController.MEAL_PLAN)
            .then()
            .statusCode(HttpStatus.OK.value())
            .and()
            .extract()
            .response()
            .as(new TypeRef<Map<String, MealMap>>() {
            }).entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue
            ));
    }
}
