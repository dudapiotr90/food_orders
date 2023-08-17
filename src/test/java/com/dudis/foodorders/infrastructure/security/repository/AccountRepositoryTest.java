package com.dudis.foodorders.infrastructure.security.repository;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.exception.RegistrationException;
import com.dudis.foodorders.infrastructure.security.entity.*;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountJpaRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountManagerJpaRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.services.RandomUUIDGenerator;
import com.dudis.foodorders.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.dudis.foodorders.utils.AccountUtils.*;
import static com.dudis.foodorders.utils.ApiRoleUtils.someApiRoleEntity1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {

    @InjectMocks
    private AccountRepository accountRepository;

    @Mock
    private AccountJpaRepository accountJpaRepository;
    @Mock
    private AccountManagerJpaRepository accountManagerJpaRepository;
    @Mock
    private AccountEntityMapper accountEntityMapper;
    @Mock
    private PasswordEncoder bCryptPasswordEncoder;
    @Mock
    private ApiRoleJpaRepository apiRoleJpaRepository;
    @Mock
    private RandomUUIDGenerator randomUUIDGenerator;

    @Test
    void findByEmailWorksCorrectly() {
        // Given
        String someEmail = "sometesting@email";
        when(accountEntityMapper.mapFromEntity(any(AccountEntity.class)))
            .thenReturn(someAccount1());
        when(accountJpaRepository.findByEmail(someEmail))
            .thenReturn(Optional.of(someAccountEntity1()))
            .thenReturn(Optional.empty());
        Optional<Account> expected = Optional.of(someAccount1());

        // When
        Optional<Account> result = accountRepository.findByEmail(someEmail);
        accountRepository.findByEmail(someEmail);
        // Then
        assertEquals(expected,result);
        verify(accountJpaRepository, times(2)).findByEmail(anyString());
        verify(accountEntityMapper, times(1)).mapFromEntity(any(AccountEntity.class));
    }
    @Test
    void findByLoginWorksCorrectly() {
        // Given
        String someLogin = "someLogin";
        when(accountEntityMapper.mapFromEntity(any(AccountEntity.class)))
            .thenReturn(someAccount1());
        when(accountJpaRepository.findByLogin(someLogin))
            .thenReturn(Optional.of(someAccountEntity1()))
            .thenReturn(Optional.empty());
        Optional<Account> expected = Optional.of(someAccount1());

        // When
        Optional<Account> result = accountRepository.findByLogin(someLogin);
        accountRepository.findByLogin(someLogin);
        // Then
        assertEquals(expected,result);
        verify(accountJpaRepository, times(2)).findByLogin(anyString());
        verify(accountEntityMapper, times(1)).mapFromEntity(any(AccountEntity.class));

    }
    @Test
    void findByRoleWorksCorrectly() {
        // Given
        Integer expected = 5;
        String someRole = "someRole";
        when(apiRoleJpaRepository.findByRole(someRole)).thenReturn(5);

        // When, Then
        Integer result = accountRepository.findByRole(someRole);
        assertEquals(expected,result);
    }
    @Test
    void findByIdWorksCorrectly() {
        // Given
        Integer someId = 456;
        when(accountEntityMapper.mapFromEntity(any(AccountEntity.class)))
            .thenReturn(someAccount1());
        when(accountJpaRepository.findById(someId))
            .thenReturn(Optional.of(someAccountEntity1()))
            .thenReturn(Optional.empty());
        Optional<Account> expected = Optional.of(someAccount1());

        // When
        Optional<Account> result = accountRepository.findById(someId);
        accountRepository.findById(someId);
        // Then
        assertEquals(expected,result);
        verify(accountJpaRepository, times(2)).findById(anyInt());
        verify(accountEntityMapper, times(1)).mapFromEntity(any(AccountEntity.class));

    }
    @Test
    void countAllAccountsWorksCorrectly() {
        // Given, When, Then
        int expected = 546;
        when(accountRepository.countAllAccounts()).thenReturn(Long.valueOf(expected));
        long result = accountRepository.countAllAccounts();
        assertEquals(expected,result);
    }
    @Test
    void updateAccountWorksCorrectly() {
        // Given
        Account someAccount = someAccount1();
        Account expected = someAccount2();
        when(accountJpaRepository.save(accountEntityMapper.mapToEntity(someAccount)))
            .thenReturn(someAccountEntity1());
        when(accountEntityMapper.mapFromEntity(someAccountEntity1()))
            .thenReturn(expected);

        // When, Then
        Account result = accountRepository.updateAccount(someAccount);
        assertEquals(expected,result);
    }
    @Test
    void prepareAccountAccessWorksCorrectly() {
        // Given
        Account someAccount = someAccount1();
        ApiRoleEntity someRole = someApiRoleEntity1();
        when(accountEntityMapper.mapToEntity(someAccount))
            .thenReturn(someAccountEntity1());
        when(bCryptPasswordEncoder.encode("another password"))
            .thenReturn("someEncodedPassword");
        AccountEntity expected = someAccountEntity2();
        expected.setAccountId(1);
        // When
        AccountEntity result = accountRepository.prepareAccountAccess(someAccount, someRole);
        // Then
        assertEquals(expected,result);

    }
    @Test
    void registerAccountWorksCorrectly() {
        // Given
        AccountEntity someAccount = someAccountEntity1();
        ApiRoleEntity someRole = someApiRoleEntity1();
        String expectedMessage = "Failed to successfully registerAccount";
        when(accountJpaRepository.findByEmail(anyString()))
            .thenReturn(Optional.of(someAccountEntity1()))
            .thenReturn(Optional.empty());
        when(accountManagerJpaRepository.saveAndFlush(any(AccountManagerEntity.class)))
            .thenReturn(new AccountManagerEntity());
        when(randomUUIDGenerator.generateUniqueCode()).thenReturn("a5e6e2a7-3b57-4b8c-9644-d9c243a3c4c1");
        ConfirmationToken expected = TokenUtils.someToken();
        // When
        ConfirmationToken result = accountRepository.registerAccount(someAccount, someRole);
        Throwable exception = assertThrows(RegistrationException.class,
            ()-> accountRepository.registerAccount(someAccount, someRole));

        // Then
        assertEquals(expected.getToken(),result.getToken());
        assertEquals(expectedMessage,exception.getMessage());

    }
    @Test
    void enableAccountWorksCorrectly() {
        // Given, When, Then
        doNothing().when(accountJpaRepository).enableAccount(anyInt());
        accountRepository.enableAccount(324);
        verify(accountJpaRepository,times(1)).enableAccount(anyInt());
    }
}