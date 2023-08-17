package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import com.dudis.foodorders.infrastructure.database.mappers.OwnerEntityMapper;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.EntityAssertionsUtils;
import com.dudis.foodorders.utils.OwnerUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerEntityMapperTest {

    private OwnerEntityMapper ownerEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        OwnerEntity expected = OwnerUtils.someOwnerEntity1();

        // When
        Owner model = ownerEntityMapper.mapFromEntity(expected);

        // Then
        EntityAssertionsUtils.assertOwnerFromEntityEquals(expected, model);

    }
    @Test
    void mapToEntityWorksCorrectly() {
        Owner expected = OwnerUtils.someOwner1();
        expected.withAccount(null);
        // When
        OwnerEntity model = ownerEntityMapper.mapToEntity(expected);

        // Then
        EntityAssertionsUtils.assertOwnerToEntityEquals(expected, model);
    }

}