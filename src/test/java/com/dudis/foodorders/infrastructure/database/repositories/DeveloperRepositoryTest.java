package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.infrastructure.database.entities.DeveloperEntity;
import com.dudis.foodorders.infrastructure.database.mappers.DeveloperEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.DeveloperJpaRepository;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.infrastructure.security.repository.AccountRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.utils.AccountUtils;
import com.dudis.foodorders.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dudis.foodorders.utils.ApiRoleUtils.someApiRoleEntity2;
import static com.dudis.foodorders.utils.DeveloperUtils.someDeveloper;
import static com.dudis.foodorders.utils.DeveloperUtils.someDeveloperEntity1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeveloperRepositoryTest {

    @InjectMocks
    private DeveloperRepository developerRepository;

    @Mock
    private DeveloperJpaRepository developerJpaRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ApiRoleJpaRepository apiRoleJpaRepository;
    @Mock
    private DeveloperEntityMapper developerEntityMapper;

    @Test
    void registerDeveloperWorksCorrectly() {
        // Given
        ConfirmationToken expected = TokenUtils.someToken();
        Developer someDeveloper = someDeveloper();
        when(apiRoleJpaRepository.findFirstByRole("DEVELOPER")).thenReturn(someApiRoleEntity2());
        when(accountRepository.prepareAccountAccess(any(Account.class), any(ApiRoleEntity.class)))
            .thenReturn(AccountUtils.someAccountEntity3());
        DeveloperEntity someDeveloperEntity = someDeveloperEntity1();
        when(developerEntityMapper.mapToEntity(any(Developer.class))).thenReturn(someDeveloperEntity);
        when(developerJpaRepository.saveAndFlush(someDeveloperEntity)).thenReturn(someDeveloperEntity);
        when(accountRepository.registerAccount(someDeveloperEntity.getAccount(), someApiRoleEntity2()))
            .thenReturn(expected);

        // When
        ConfirmationToken result = developerRepository.registerDeveloper(someDeveloper);
        // Then
        assertEquals(expected,result);

    }
    @Test
    void findDeveloperByAccountIdWorksCorrectly() {
        // Given
        Optional<Developer> expected = Optional.of(someDeveloper());
        when(developerJpaRepository.findByAccountId(anyInt())).thenReturn(Optional.of(someDeveloperEntity1()));
        when(developerEntityMapper.mapFromEntity(any(DeveloperEntity.class))).thenReturn(someDeveloper());

        // When
        Optional<Developer> result = developerRepository.findDeveloperByAccountId(472747);
        // Then
        assertEquals(expected,result);
    }

}