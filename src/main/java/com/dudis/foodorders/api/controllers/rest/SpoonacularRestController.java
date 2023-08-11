package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.infrastructure.spoonacular.Diet;
import com.dudis.foodorders.infrastructure.spoonacular.MealMap;
import com.dudis.foodorders.services.SpoonacularService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(DeveloperEndpoint.DEV)
public class SpoonacularRestController {

    public static final String MEAL_PLAN = "/spoonacular/mealPlan";

    private final SpoonacularService spoonacularService;

    @Operation(summary = "Generate Meal Plan")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Plan generated",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Wrong input data",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "409",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "502",
            content = @Content(mediaType = "application/json")
        )
    })
    @GetMapping(MEAL_PLAN)
    public ResponseEntity<Map<String, MealMap>> genMealPlan(
        @Parameter(
            description = "Time frame for generated plan(day or week), day by default",
            example = "day",
            allowEmptyValue = true
        )
        @RequestParam(required = false) String timeFrame,
        @Parameter(
            description = "Number of calories you want to consume per day",
            example = "2500",
            allowEmptyValue = true
        )
        @RequestParam(required = false) BigDecimal caloriesPerDay,
        @Parameter(
            description = "Diet type",
            schema = @Schema(implementation = Diet.class),
            example = "Vegan"
        )
        @RequestParam Diet diet,
        @Parameter(
            description = "Which ingredients to exclude. Comma separated",
            example = "eggs, apples",
            allowEmptyValue = true
        )
        @RequestParam(required = false) String exclude
    ) throws JsonProcessingException {
        Map<String, MealMap> mealPlan = spoonacularService.getMealPlan(timeFrame, caloriesPerDay, diet, exclude);
        return ResponseEntity.ok(mealPlan);
    }
}
