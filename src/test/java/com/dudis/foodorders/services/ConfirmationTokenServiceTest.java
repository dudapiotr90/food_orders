package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.domain.exception.RegistrationException;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntityMapper;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.ConfirmationTokenDAO;
import com.dudis.foodorders.utils.AccountUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dudis.foodorders.utils.TokenUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceTest {

    @InjectMocks
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private ConfirmationTokenDAO confirmationTokenDAO;
    @Mock
    private AccountEntityMapper accountEntityMapper;
    @Mock
    private AccountService accountService;

    @Test
    void saveConfirmationTokenWorksCorrectly() {
        // Given
        ConfirmationToken someToken = someToken();
        String expected = "a5e6e2a7-3b57-4b8c-9644-d9c243a3c4c1";
        when(confirmationTokenDAO.save(any(ConfirmationToken.class))).thenReturn(expected);
        // When
        String result = confirmationTokenService.saveConfirmationToken(someToken);
        // Then
        assertEquals(expected, result);
    }

    @Test
    void confirmTokenWorksCorrectly() {
        // Given
        Account expected = AccountUtils.someAccount();
        String someRandomToken = UUID.randomUUID().toString();
        ConfirmationToken someConfirmationToken = someToken().withExpiresAt(OffsetDateTime.now().plusMinutes(30));
        when(confirmationTokenDAO.getToken(someRandomToken)).thenReturn(Optional.ofNullable(someConfirmationToken));
        doNothing().when(confirmationTokenDAO).setConfirmedAt(anyString(),any(OffsetDateTime.class));
        assert someConfirmationToken != null;
        doNothing().when(accountService).enableAccount(anyInt());
        when(accountEntityMapper.mapFromEntity(any(AccountEntity.class))).thenReturn(expected);
        // when
        Account result = confirmationTokenService.confirmToken(someRandomToken);

        // Then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    void confirmTokenThrowsCorrectly(ConfirmationToken someToken,String expectedMessage) {
        // Given
        String someRandomToken = UUID.randomUUID().toString();
        when(confirmationTokenDAO.getToken(someRandomToken)).thenReturn(Optional.ofNullable(someToken));

        // Then, When
        if (Optional.ofNullable(someToken).isEmpty()) {
            Throwable exception = assertThrows(NotFoundException.class, () -> confirmationTokenService.confirmToken(someRandomToken));
            assertEquals(expectedMessage.formatted(someRandomToken),exception.getMessage());
        } else if (Objects.nonNull(someToken.getConfirmedAt())) {
            Throwable exception = assertThrows(RegistrationException.class, () -> confirmationTokenService.confirmToken(someRandomToken));
            assertEquals(expectedMessage,exception.getMessage());
        } else {
            Throwable exception = assertThrows(RegistrationException.class, () -> confirmationTokenService.confirmToken(someRandomToken));
            assertEquals(expectedMessage,exception.getMessage());
        }
    }

    public static Stream<Arguments> confirmTokenThrowsCorrectly() {
        return Stream.of(
            Arguments.of(null, "Token [%s] not found"),
            Arguments.of(someToken().withConfirmedAt(OffsetDateTime.of(2023, 8, 8, 6, 50, 0, 0, ZoneOffset.UTC)), "Email already confirmed"),
            Arguments.of(someToken().withExpiresAt(OffsetDateTime.now().minusMinutes(30)), "Token expired")
        );
    }
}