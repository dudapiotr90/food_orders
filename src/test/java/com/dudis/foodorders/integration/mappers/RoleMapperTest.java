package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.RoleDTO;
import com.dudis.foodorders.api.mappers.RoleMapper;
import com.dudis.foodorders.infrastructure.security.Role;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static com.dudis.foodorders.infrastructure.security.Role.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {EntityMappersTestConfig.class})
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RoleMapperTest {
    private RoleMapper roleMapper;

    public static Stream<Arguments> mapToDTOWorksCorrectly() {
        return Stream.of(
            Arguments.of(CUSTOMER,RoleDTO.CUSTOMER),
            Arguments.of(OWNER,RoleDTO.OWNER),
            Arguments.of(DEVELOPER,RoleDTO.DEVELOPER)
        );
    }

    @ParameterizedTest
    @MethodSource
    void mapToDTOWorksCorrectly(Role expected, RoleDTO result) {
        // Given, When, Then

        // Then
        Assertions.assertEquals(expected.name(), result.name());
    }
}