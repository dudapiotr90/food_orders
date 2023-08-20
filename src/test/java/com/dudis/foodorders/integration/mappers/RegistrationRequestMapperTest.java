package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RegistrationRequestMapperTest {

//    private RegistrationRequestMapper registrationRequestMapper;
//    @Test
//    void mapToDTOWorksCorrectly()  {
//        // Given
//        Owner expected = OwnerUtils.someOwner1();
//        // When
//        OwnerDTO model = registrationRequestMapper.mapToDTO(expected);
//
//        // Then
//        DTOAssertionsUtils.assertOwnerToDTOEquals(expected, model);
//
//    }
//    @Test
//    void mapFromDTOWorksCorrectly()  {
//        // Given
//        OwnerDTO expected = OwnerUtils.someOwnerDTO1();
//        // When
//        Owner model = registrationRequestMapper.mapFromDTO(expected);
//
//        // Then
//        DTOAssertionsUtils.assertOwnerFromDTOEquals(expected, model);
//
//    }

}