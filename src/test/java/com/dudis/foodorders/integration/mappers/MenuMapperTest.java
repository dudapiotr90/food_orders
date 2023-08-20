package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.DTOAssertionsUtils;
import com.dudis.foodorders.utils.MenuUtils;
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
class MenuMapperTest {

    private MenuMapper menuMapper;

    @Test
    void mapToDTOWorksCorrectly() {
        // Given
        Menu expected = MenuUtils.someMenu1();
        byte[] bytes = new byte[]{1};
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.readAllBytes(any(Path.class))).thenReturn(bytes);
            // When
            MenuDTO model = menuMapper.mapToDTO(expected);

            // Then
            DTOAssertionsUtils.assertMenuToDTOEquals(expected, model);
        }
    }
}