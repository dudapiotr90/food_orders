package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.EntityAssertionsUtils;
import com.dudis.foodorders.utils.MenuUtils;
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
class MenuEntityMapperTest {

    private MenuEntityMapper menuEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        MenuEntity expected = MenuUtils.someMenuEntity1();

        // When
        Menu model = menuEntityMapper.mapFromEntity(expected);

        // Then
        EntityAssertionsUtils.assertMenuFromEntityEquals(expected, model);

    }
    @Test
    void mapToEntityWorksCorrectly() {
        Menu expected = MenuUtils.someMenu1();

        // When
        MenuEntity model = menuEntityMapper.mapToEntity(expected);

        // Then
        Assertions.assertEquals(expected.getMenuId(),model.getMenuId());
        Assertions.assertEquals(expected.getMenuName(),model.getMenuName());
        Assertions.assertEquals(expected.getDescription(),model.getDescription());
        Assertions.assertNull(model.getFoods());
    }

}