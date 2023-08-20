package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.mappers.OffsetDateTimeMapper;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OffsetDateTimeMapperTest {

    private OffsetDateTimeMapper offsetDateTimeMapper;
    @Test
    void mapOffsetDateTimeToStringWorksCorrectly() {
        // Given
        OffsetDateTime someTime = OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC);
        String expected = "2023-08-08 06:30:00";

        // When
        String result = offsetDateTimeMapper.mapOffsetDateTimeToString(someTime);

        // Then
        Assertions.assertEquals(expected,result);
    }

}