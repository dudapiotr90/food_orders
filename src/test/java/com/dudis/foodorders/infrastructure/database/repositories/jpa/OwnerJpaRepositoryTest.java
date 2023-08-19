package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import com.dudis.foodorders.integration.configuration.PersistanceContainerTestConfiguration;
import com.dudis.foodorders.utils.AccountUtils;
import com.dudis.foodorders.utils.OwnerUtils;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(PersistanceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OwnerJpaRepositoryTest {

     private OwnerJpaRepository ownerJpaRepository;

     @Test
     void findByAccountIdWorksCorrectly() {
          // Given
          OwnerEntity ownerToFind = OwnerUtils.someOwnerEntity1();
          ownerToFind.setAccount(AccountUtils.someAccountToPersist());
          ownerToFind.setOwnerId(null);
          ownerJpaRepository.saveAndFlush(ownerToFind);

          // When
          Optional<OwnerEntity> result = ownerJpaRepository.findByAccountId(ownerToFind.getAccount().getAccountId());

          // Then
          assertThat(result).isNotEmpty();
          assertThat(result.get()).isEqualTo(ownerToFind);
     }
}