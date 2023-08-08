package com.dudis.foodorders.infrastructure.configuration;

import com.dudis.foodorders.infrastructure.spoonacular.ApiClient;
import com.dudis.foodorders.infrastructure.spoonacular.api.MiscApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${api.spoonacular.url}")
    private String spoonacularUrl;

    @Value("${api.spoonacular.apiKey}")
    private String spoonacularApiKey;

    @Bean
    public WebClient spoonacularApiClient(final ObjectMapper objectMapper) {
        final var exchangeStrategies = ExchangeStrategies
            .builder()
            .codecs(configurer -> {
                configurer
                    .defaultCodecs()
                    .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                configurer
                    .defaultCodecs()
                    .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
            })
            .build();

        return WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .build();
    }

    @Bean
    public ApiClient apiClient(final WebClient webClient) {
        ApiClient apiClient = new ApiClient(webClient);
        apiClient.setBasePath(spoonacularUrl);
        apiClient.addDefaultHeader("apiKey", spoonacularApiKey);
        return apiClient;
    }

    @Bean
    public MiscApi getMiscApi(final ApiClient apiClient) {
        apiClient.setApiKey(spoonacularApiKey);
        return new MiscApi(apiClient);
    }

}
