package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.dtos.RestaurantForCustomerDTO;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.DTOAssertionsUtils;
import com.dudis.foodorders.utils.RestaurantUtils;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {EntityMappersTestConfig.class})
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantMapperTest {
    private RestaurantMapper restaurantMapper;

    @Test
    void mapToDTOWorksCorrectly() {
        // Given
        Restaurant expected = RestaurantUtils.someRestaurant4();
        byte[] bytes = new byte[]{1};
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.readAllBytes(any(Path.class))).thenReturn(bytes);
            // When
            RestaurantDTO model = restaurantMapper.mapToDTO(expected);

            // Then
            DTOAssertionsUtils.assertRestaurantToDTOEquals(expected, model);
        }
    }

    @Test
    void mapFromDTOWorksCorrectly() {
        // Given
        RestaurantDTO expected = RestaurantUtils.someRestaurantDTO4();
        // When
        Restaurant model = restaurantMapper.mapFromDTO(expected);

        // Then
        DTOAssertionsUtils.assertRestaurantFromDTOEquals(expected, model);

    }


    @Test
    void mapToDTOForCustomerWorksCorrectly() {
        // Given
        Restaurant expected = RestaurantUtils.someRestaurant1();
        // When
        RestaurantForCustomerDTO model = restaurantMapper.mapToDTOForCustomer(expected);
        // Then
        DTOAssertionsUtils.assertRestaurantForCustomerToDTOEquals(expected, model);
    }
}