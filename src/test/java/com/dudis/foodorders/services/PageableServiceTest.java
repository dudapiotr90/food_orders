package com.dudis.foodorders.services;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PageableServiceTest {

    @InjectMocks
    private PageableService pageableService;

    @Test
    void preparePageableThrowsCorrectly() {
        // Given
        String expectedMessage = "SortHow accepts only: {asc,desc}";

        // When
        Throwable exception = assertThrows(ValidationException.class,
            () -> pageableService.preparePageable(3, 65, "sdfgsjfgs", "something to sort"));
        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void preparePageableWorksCorrectly(Pageable expected, Integer pageNumber, Integer pageSize, String sortHow, String[] sortBy) {
        // Given, When
        Pageable result = pageableService.preparePageable(pageNumber, pageSize, sortHow, sortBy);

        // Then
        assertEquals(expected, result);
    }

    public static Stream<Arguments> preparePageableWorksCorrectly() {
        return Stream.of(
            Arguments.of(PageRequest.of(0, 2), null, null, "asc",null),
            Arguments.of(PageRequest.of(3, 12, Sort.by("someField").descending()),
                4, 12, "desc", new String[]{"someField"}),
            Arguments.of(PageRequest.of(11, 65, Sort.by("someField1", "someField2").descending()),
                12, 65, "desc", new String[]{"someField1","someField2"})
        );
    }
}