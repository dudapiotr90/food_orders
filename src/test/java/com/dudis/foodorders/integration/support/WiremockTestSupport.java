package com.dudis.foodorders.integration.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Map;

public interface WiremockTestSupport {

    String RANDOM_API_KEY = "0cc7ff8c36614fef9237a26097f2c7ad";

    Map<String, String> responseBodies = Map.of(
        "day", "randomMealPlanForDay.json",
        "week", "randomMealPlanForWeek.json"
    );

    default void stubForMealPlan(
        final WireMockServer wireMockServer,
        final String timeFrame,
        final BigDecimal targetCalories,
        final String diet,
        final String exclude
    ) {
        wireMockServer.stubFor(WireMock.get(
                WireMock.urlPathMatching("/mealplanner/generate?"))
            .withHeader("x-api-key", WireMock.equalTo(RANDOM_API_KEY))
            .withQueryParams(getParams(timeFrame, targetCalories, diet, exclude))
            .willReturn(WireMock.aResponse()
                .withBodyFile("wireMock/%s".formatted(responseBodies.get(timeFrame)))
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withStatus(HttpStatus.OK.value()))
        );
    }

    default Map<String, StringValuePattern> getParams(
        String timeFrame,
        BigDecimal targetCalories,
        String diet,
        String exclude
    ) {
        return Map.of(
            "timeFrame", WireMock.equalTo(timeFrame),
            "targetCalories", WireMock.equalTo(targetCalories.toString()),
            "diet", WireMock.equalTo(diet),
            "exclude", WireMock.equalTo(exclude)
        );
    }
}
