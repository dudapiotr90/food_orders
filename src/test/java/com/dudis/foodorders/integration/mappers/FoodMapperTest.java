package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.mappers.FoodMapper;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.FoodUtils;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {EntityMappersTestConfig.class})
@AllArgsConstructor(onConstructor = @__(@Autowired))
class FoodMapperTest {

    private FoodMapper foodMapper;

    @Test
    void mapToDTOWorksCorrectly() {
        // Given
        Food expected = FoodUtils.someFood1();
        byte[] bytes = new byte[]{1};

        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.readAllBytes(any(Path.class))).thenReturn(bytes);
            // When
            FoodDTO model = foodMapper.mapToDTO(expected);

            // Then
            assertEquals(expected.getFoodId(), model.getFoodId());
            assertEquals(expected.getName(), model.getName());
            assertEquals(expected.getDescription(), model.getDescription());
            assertEquals(expected.getPrice(), model.getPrice());
            assertEquals(expected.getFoodType(), model.getFoodType());
            assertEquals(expected.getFoodImagePath(), model.getFoodImagePath());
        }
    }

    @Test
    void mapFromDTOWorksCorrectly() {
        // Given
        FoodDTO expected = FoodUtils.someFoodDTO1();
        // When
        Food model = foodMapper.mapFromDTO(expected);

        // Then
        assertEquals(expected.getFoodId(), model.getFoodId());
        assertEquals(expected.getName(), model.getName());
        assertEquals(expected.getDescription(), model.getDescription());
        assertEquals(expected.getPrice(), model.getPrice());
        assertEquals(expected.getFoodType(), model.getFoodType());
        assertEquals(expected.getFoodImagePath(), model.getFoodImagePath());
        assertNull(model.getMenu());
    }

}