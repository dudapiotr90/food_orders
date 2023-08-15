package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.services.dao.AccountDAO;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dudis.foodorders.utils.AccountUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountDAO accountDAO;

    @Test
    void findByEmailWorksCorrectly() {
        // Given
        String someEmail1 = "some@mail";
        String someEmail2 = "";
        Account someAccount = someAccount();
        when(accountDAO.findByEmail(someEmail1)).thenReturn(Optional.of(someAccount));
        when(accountDAO.findByEmail(someEmail2)).thenReturn(Optional.empty());

        // When
        Account result1 = accountService.findByEmail(someEmail1);
        Account result2 = accountService.findByEmail(someEmail2);

        // Then
        assertEquals(someAccount, result1);
        assertNull(result2);
    }

    @Test
    void enableAccountWorksCorrectly() {
        // Given
        Integer accountId = 1;
        doNothing().when(accountDAO).enableAccount(anyInt());

        // When
        accountService.enableAccount(accountId);

        // Then
        verify(accountDAO, times(1)).enableAccount(accountId);
    }

    @Test
    void findByLoginWorksCorrectly() {
        // Given
        String someLogin1 = "someLogin";
        String someLogin2 = "asd";
        Account someAccount = someAccount();
        String exceptionMessage = String.format("User with login: [%s] doesn't exists", someLogin2);
        when(accountDAO.findByLogin(someLogin1)).thenReturn(Optional.of(someAccount));
        when(accountDAO.findByLogin(someLogin2)).thenReturn(Optional.empty());

        // When
        Account result1 = accountService.findByLogin(someLogin1);
        Throwable notFoundException = assertThrows(NotFoundException.class, () -> accountService.findByLogin(someLogin2));

        // Then
        assertEquals(someAccount, result1);
        assertEquals(exceptionMessage, notFoundException.getMessage());
    }

    @Test
    void buildAccountWorksCorrectly() {
        // Given
        Account someAccount = someAccount();
        RegistrationRequest someRequest = someRegistrationRequest();
        RegistrationRequest someRequest2 = someRegistrationRequest().withUserConfirmPassword("notMatching");
        when(accountDAO.findByRole(someRequest.getRole())).thenReturn(1);
        String exceptionMessage = "Passwords input do not match";

        // When
        Account result1 = accountService.buildAccount(someRequest);
        Throwable exception = assertThrows(ValidationException.class, () -> accountService.buildAccount(someRequest2));

        // Then
        assertEquals(exceptionMessage, exception.getMessage());
        assertEquals(someAccount, result1);
    }

    @Test
    void updateAccountWorksCorrectly() {
        // Given
        Account someAccount = someAccount();
        Account updatedAccount = someAccount().withLogin("updatedLogin");
        UpdateAccountDTO someRequest = someUpdateRequest();
        UpdateAccountDTO someRequest2 = someUpdateRequest().withUserEmail("");
        UpdateAccountDTO updatedDetails = someUpdateRequest2().withNewUserLogin("updatedLogin");

        when(accountDAO.findByEmail(someRequest.getUserEmail())).thenReturn(Optional.of(someAccount));
        when(accountDAO.findByEmail(someRequest2.getUserEmail())).thenReturn(Optional.empty());
        when(accountDAO.updateAccount(someAccount)).thenReturn(updatedAccount);
        String exceptionMessage = "Can't update non existing account";

        // When
        UpdateAccountDTO result1 = accountService.updateAccount(someRequest);
        Throwable exception = assertThrows(NotFoundException.class, () -> accountService.updateAccount(someRequest2));

        // Then
        assertEquals(exceptionMessage, exception.getMessage());
        assertEquals(updatedDetails, result1);
    }

    @Test
    void countAllAccountsWorksCorrectly() {
        // Given
        long countedAccounts = 5;
        when(accountDAO.countAllAccounts()).thenReturn(5L);
        // When
        long result = accountService.countAllAccounts();

        // Then
        assertEquals(countedAccounts,result);
        verify(accountDAO, times(1)).countAllAccounts();
    }




    @Test
    void findCustomerAccountWorksCorrectly() {
        // Given
        Account someAccount = someAccount().withRoleId(3);
        when(accountDAO.findById(1)).thenReturn(Optional.of(someAccount));
        when(accountDAO.findById(3)).thenReturn(Optional.empty());
        String exceptionMessage = "Account with id: [%s] doesn't exist".formatted(3);
        // When
        Account result = accountService.findCustomerAccount(1);
        Throwable exception = assertThrows(NotFoundException.class, () -> accountService.findCustomerAccount(3));

        // Then
        assertEquals(exceptionMessage,exception.getMessage());
        assertEquals(someAccount(), result);
    }
}