package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import com.dudis.foodorders.infrastructure.database.mappers.OrderItemEntityMapper;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.EntityAssertionsUtils;
import com.dudis.foodorders.utils.OrderItemsUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OrderItemEntityMapperTest {

    private OrderItemEntityMapper orderItemEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        OrderItemEntity expected = OrderItemsUtils.someOrderItemEntity3();

        // When
        OrderItem model = orderItemEntityMapper.mapFromEntity(expected);

        // Then
        assertTrue(EntityAssertionsUtils.assertOrderItemFromEntityEquals(expected, model));

    }
    @Test
    void mapToEntityWorksCorrectly() {
        // Given
        OrderItem expected = OrderItemsUtils.someOrderItem1();

        // When
        OrderItemEntity model = orderItemEntityMapper.mapToEntity(expected);

        // Then
        assertTrue(EntityAssertionsUtils.assertOrderItemToEntityEquals(expected, model));
    }

}