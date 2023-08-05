package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity,Integer> {

    @Query("""
        SELECT oe FROM OwnerEntity oe
        JOIN FETCH oe.account ac
        WHERE ac.accountId = :accountId
        """)
    Optional<OwnerEntity> findByAccountId(@Param("accountId") Integer accountId);

}
