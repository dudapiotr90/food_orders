package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import com.dudis.foodorders.infrastructure.database.mappers.OwnerEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OwnerJpaRepository;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.infrastructure.security.repository.AccountRepository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.utils.AccountUtils;
import com.dudis.foodorders.utils.OwnerUtils;
import com.dudis.foodorders.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.dudis.foodorders.utils.ApiRoleUtils.someApiRoleEntity2;
import static com.dudis.foodorders.utils.OwnerUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerRepositoryTest {

    @InjectMocks
    private OwnerRepository ownerRepository;

    @Mock
    private OwnerJpaRepository ownerJpaRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ApiRoleJpaRepository apiRoleJpaRepository;
    @Mock
    private OwnerEntityMapper ownerEntityMapper;

    @Test
    void registerOwnerWorksCorrectly() {
        // Given
        ConfirmationToken expected = TokenUtils.someToken();
        Owner someOwner = someOwner1();
        when(apiRoleJpaRepository.findFirstByRole("OWNER")).thenReturn(someApiRoleEntity2());
        when(accountRepository.prepareAccountAccess(any(Account.class), any(ApiRoleEntity.class)))
            .thenReturn(AccountUtils.someAccountEntity1());
        OwnerEntity someOwnerEntity = OwnerUtils.someOwnerEntity1();
        when(ownerEntityMapper.mapToEntity(any(Owner.class))).thenReturn(someOwnerEntity);
        when(ownerJpaRepository.saveAndFlush(someOwnerEntity)).thenReturn(someOwnerEntity);
        when(accountRepository.registerAccount(someOwnerEntity.getAccount(), someApiRoleEntity2()))
            .thenReturn(expected);

        // When
        ConfirmationToken result =ownerRepository.registerOwner(someOwner);
        // Then
        assertEquals(expected,result);

    }
    @Test
    void findOwnerByAccountIdWorksCorrectly() {
        // Given
        Optional<Owner> expected = Optional.of(someOwner1());
        when(ownerJpaRepository.findByAccountId(anyInt())).thenReturn(Optional.of(someOwnerEntity1()));
        when(ownerEntityMapper.mapFromEntity(any(OwnerEntity.class))).thenReturn(someOwner1());

        // When
        Optional<Owner> result = ownerRepository.findOwnerByAccountId(54);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void findOwnerByIdWorksCorrectly() {
        // Given
        Optional<Owner> expected = Optional.of(someOwner1());
        when(ownerJpaRepository.findById(anyInt())).thenReturn(Optional.of(someOwnerEntity1()));
        when(ownerEntityMapper.mapFromEntity(any(OwnerEntity.class))).thenReturn(someOwner1());

        // When
        Optional<Owner> result = ownerRepository.findOwnerById(54);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void findAllOwnersWorksCorrectly() {
        // Given
        Pageable pageable = PageRequest.of(2, 6);
        Page<OwnerEntity> ownerEntities = new PageImpl<>(someOwnerEntities());
        Page<Owner> expected = new PageImpl<>(someOwners());
        when(ownerJpaRepository.findAll(any(Pageable.class))).thenReturn(ownerEntities);
        when(ownerEntityMapper.mapFromEntity(any(OwnerEntity.class)))
            .thenReturn(someOwner1(),someOwner2(),someOwner3());

        // When
        Page<Owner> result = ownerRepository.findAllOwners(pageable);
        // Then
        assertEquals(expected,result);
    }
}