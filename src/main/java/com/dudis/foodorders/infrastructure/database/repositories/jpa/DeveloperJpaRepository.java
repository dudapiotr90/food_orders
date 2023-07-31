package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.DeveloperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperJpaRepository extends JpaRepository<DeveloperEntity, Integer> {
    @Query("""
        SELECT d FROM DeveloperEntity d
        JOIN FETCH d.account ac
        WHERE ac.accountId = :accountId
        """)
    Optional<DeveloperEntity> findByAccountId(@Param("accountId") Integer accountId);
}
