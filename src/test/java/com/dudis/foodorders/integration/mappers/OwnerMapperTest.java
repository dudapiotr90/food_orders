package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.mappers.OwnerMapper;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.DTOAssertionsUtils;
import com.dudis.foodorders.utils.OwnerUtils;
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
class OwnerMapperTest {

    private OwnerMapper ownerMapper;
    @Test
    void mapToDTOWorksCorrectly()  {
        // Given
        Owner expected = OwnerUtils.someOwner1();
        // When
        OwnerDTO model = ownerMapper.mapToDTO(expected);

        // Then
        DTOAssertionsUtils.assertOwnerToDTOEquals(expected, model);

    }
    @Test
    void mapFromDTOWorksCorrectly()  {
        // Given
        OwnerDTO expected = OwnerUtils.someOwnerDTO1();
        // When
        Owner model = ownerMapper.mapFromDTO(expected);

        // Then
        DTOAssertionsUtils.assertOwnerFromDTOEquals(expected, model);

    }

}