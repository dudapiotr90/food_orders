package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryAddressEntity;
import com.dudis.foodorders.infrastructure.database.entities.DeveloperEntity;
import com.dudis.foodorders.infrastructure.database.mappers.DeveloperEntityMapper;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.AssertionsUtils;
import com.dudis.foodorders.utils.DeveloperUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.dudis.foodorders.utils.DeliveryAddressUtils.someDeliveryAddress1;
import static com.dudis.foodorders.utils.DeliveryAddressUtils.someDeliveryAddressEntity1;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeveloperEntityMapperTest {

    private DeveloperEntityMapper developerEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        DeveloperEntity expected = DeveloperUtils.someDeveloperEntity1();

        // When
        Developer model = developerEntityMapper.mapFromEntity(expected);

        // Then
        AssertionsUtils.assertDeveloperFromEntityEquals(expected, model);

    }
    @Test
    void mapToEntityWorksCorrectly() {
        Developer expected = DeveloperUtils.someDeveloper();

        // When
        DeveloperEntity model = developerEntityMapper.mapToEntity(expected);

        // Then
        AssertionsUtils.assertDeveloperToEntityEquals(expected, model);
    }

}