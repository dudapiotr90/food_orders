package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.mappers.FoodEntityMapper;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.EntityAssertionsUtils;
import com.dudis.foodorders.utils.FoodUtils;
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
class FoodEntityMapperTest {

    private FoodEntityMapper foodEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        FoodEntity expected = FoodUtils.someFoodEntity1();

        // When
        Food model = foodEntityMapper.mapFromEntity(expected);

        // Then
        Assertions.assertTrue(EntityAssertionsUtils.assertFoodFromEntityEquals(expected, model));

    }
    @Test
    void mapToEntityWorksCorrectly() {
        Food expected = FoodUtils.someFood1();

        // When
        FoodEntity model = foodEntityMapper.mapToEntity(expected);

        // Then
        Assertions.assertTrue(EntityAssertionsUtils.assertFoodToEntityEqualsReturnsTrue(expected, model));
    }

}