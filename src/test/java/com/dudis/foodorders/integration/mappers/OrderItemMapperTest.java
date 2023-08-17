package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.DTOAssertionsUtils;
import com.dudis.foodorders.utils.OrderItemsUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.any;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OrderItemMapperTest {
    private OrderItemMapper orderItemMapper;

    @Test
    void mapToDTOWorksCorrectly()  {
        // Given
        OrderItem expected = OrderItemsUtils.someOrderItem1();

        byte[] bytes = new byte[]{1};
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.readAllBytes(any(Path.class))).thenReturn(bytes);
            // When
            OrderItemDTO model = orderItemMapper.mapToDTO(expected);

            // Then
            DTOAssertionsUtils.assertOrderItemToDTOEquals(expected, model);
        }
    }

    @Test
    void mapFromDTOWorksCorrectly()  {
        // Given
        OrderItemDTO expected = OrderItemsUtils.someOrderItemDTO1();
        // When
        OrderItem model = orderItemMapper.mapFromDTO(expected);

        // Then
        DTOAssertionsUtils.assertOrderItemFromDTOEquals(expected, model);

    }

}