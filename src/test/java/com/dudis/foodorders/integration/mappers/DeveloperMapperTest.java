package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.DeveloperDTO;
import com.dudis.foodorders.api.mappers.DeveloperMapper;
import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.DTOAssertionsUtils;
import com.dudis.foodorders.utils.DeveloperUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DeveloperMapperTest {

    private DeveloperMapper developerMapper;
    @Test
    void mapToDTOWorksCorrectly() {
        // Given
        Developer expected = DeveloperUtils.someDeveloper();
        // When
        DeveloperDTO model = developerMapper.mapToDTO(expected);

        // Then
        DTOAssertionsUtils.assertDeveloperToDTOEquals(expected, model);

    }

}