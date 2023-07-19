package com.dudis.foodorders.infrastructure.security.repository.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity,Integer> {

    Optional<AccountEntity> findByEmail(String email);

    @EntityGraph(
        type = EntityGraph.EntityGraphType.FETCH,
        attributePaths = {
            "roles"
        }
    )
    Optional<AccountEntity> findByLogin(String login);

    @Modifying
    @Query("UPDATE AccountEntity a " +
        "SET a.enabled = TRUE WHERE a.accountId = ?1")
    void enableAccount(Integer accountId);
}
