package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import com.dudis.foodorders.infrastructure.database.mappers.CartEntityMapper;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.CartUtils;
import com.dudis.foodorders.utils.EntityAssertionsUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CartEntityMapperTest {

    private CartEntityMapper cartEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        CartEntity expected = CartUtils.someCartEntity();

        // When
        Cart model = cartEntityMapper.mapFromEntity(expected);

        // Then
        EntityAssertionsUtils.assertCartFromEntityEquals(expected, model);

    }
    @Test
    void mapToEntityWorksCorrectly() {
        Cart expected = CartUtils.someCart();

        // When
        CartEntity model = cartEntityMapper.mapToEntity(expected);

        // Then
        EntityAssertionsUtils.assertCartToEntityEquals(expected, model);
    }

}