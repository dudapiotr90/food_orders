package com.dudis.foodorders.integration;

import com.dudis.foodorders.api.mappers.DTOMappersMarker;
import com.dudis.foodorders.infrastructure.database.mappers.EntityMappersMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {EntityMappersMarker.class, DTOMappersMarker.class})
public class EntityMappersTestConfig {
}
