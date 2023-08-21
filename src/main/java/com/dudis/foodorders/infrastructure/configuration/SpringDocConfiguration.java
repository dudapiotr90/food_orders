package com.dudis.foodorders.infrastructure.configuration;

import com.dudis.foodorders.FoodOrdersApplication;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
            .group("developer")
            .pathsToMatch("/**")
            .packagesToScan(FoodOrdersApplication.class.getPackageName())
            .build();
    }

    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                .title("Food Orders Api")
                .contact(contact())
                .version("v1.0"));
    }

    private Contact contact() {
        return new Contact()
            .name("Piotr")
            .email("dudapiotr90@gmail.com");
    }
}
