package com.dudis.foodorders.integration;

import com.dudis.foodorders.infrastructure.database.mappers.Marker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Marker.class)
public class EntityMappersTestConfig {
}
