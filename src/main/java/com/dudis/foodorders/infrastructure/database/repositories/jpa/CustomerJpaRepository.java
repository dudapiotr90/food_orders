package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Customer;
import org.apache.el.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;

import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity,Integer> {
    @Query("""
        SELECT c FROM CustomerEntity c
        JOIN FETCH c.account ac
        WHERE ac.accountId = :accountId
        """)
    Optional<CustomerEntity> findByAccountId(@Param("accountId")Integer accountId);
}
