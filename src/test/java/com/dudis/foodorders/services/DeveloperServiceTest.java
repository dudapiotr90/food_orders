package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.DeveloperDAO;
import com.dudis.foodorders.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dudis.foodorders.utils.AccountUtils.someAccount1;
import static com.dudis.foodorders.utils.AccountUtils.someRegistrationRequest;
import static com.dudis.foodorders.utils.DeveloperUtils.someDeveloper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeveloperServiceTest {

    @InjectMocks
    private DeveloperService developerService;

    @Mock
    private DeveloperDAO developerDAO;
    @Mock
    private AccountService accountService;

    @Test
    void registerDeveloperWorksCorrectly() {
        // Given
        ConfirmationToken expected = TokenUtils.someToken();
        RegistrationRequestDTO someRequest = someRegistrationRequest().withRole("DEVELOPER");
        Account someAccount = someAccount1();
        when(accountService.buildAccount(someRequest)).thenReturn(someAccount);
        when(developerDAO.registerDeveloper(any(Developer.class))).thenReturn(expected);
        // When
        ConfirmationToken result = developerService.registerDeveloper(someRequest);

        // Then
        verify(accountService, times(1)).buildAccount(someRequest);
        verify(developerDAO, times(1)).registerDeveloper(any(Developer.class));
        assertEquals(expected, result);
    }

    @Test
    void findDeveloperByAccountIdWorksCorrectly() {
        // Given
        Developer developer1 = someDeveloper();
        when(developerDAO.findDeveloperByAccountId(anyInt())).thenReturn(Optional.of(developer1));

        // When
        developerService.findDeveloperByAccountId(5789);

        // Then
        verify(developerDAO, times(1)).findDeveloperByAccountId(anyInt());

    }

    @Test
    void findDeveloperByAccountIdThrowsCorrectly() {
        // Given
        String expectedMessage = "Developer doesn't exists. Please register your account";
        when(developerDAO.findDeveloperByAccountId(anyInt())).thenReturn(Optional.empty());
        // When
        Throwable exception = assertThrows(NotFoundException.class,
            () -> developerService.findDeveloperByAccountId(54));
        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }
}