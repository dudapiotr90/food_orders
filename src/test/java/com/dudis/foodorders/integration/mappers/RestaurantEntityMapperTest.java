package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.EntityAssertionsUtils;
import com.dudis.foodorders.utils.RestaurantUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantEntityMapperTest {

    private RestaurantEntityMapper restaurantEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        RestaurantEntity expected = RestaurantUtils.someRestaurantEntity1();


        // When
        Restaurant model = restaurantEntityMapper.mapFromEntity(expected);

        // Then
        EntityAssertionsUtils.assertRestaurantFromEntityEquals(expected, model);

    }
    @Test
    void mapToEntityWorksCorrectly() {
        Restaurant expected = RestaurantUtils.someRestaurant4();

        // When
        RestaurantEntity model = restaurantEntityMapper.mapToEntity(expected);

        // Then
        EntityAssertionsUtils.assertRestaurantToEntityEquals(expected, model);
    }

    @Test
    void buildRestaurantFromObjectWorksCorrectly() {
        // Given, When
        Object[] someObject = RestaurantUtils.someRestaurantAsObject1();
        someObject[0] = 1;
        Restaurant expected = RestaurantUtils.someRestaurantFromObject();
        Restaurant result = restaurantEntityMapper.buildRestaurantFromObject(someObject);
        // Then
        Assertions.assertEquals(expected,result);
    }

}