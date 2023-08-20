package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity,Integer> {
    @Query("""
        SELECT c FROM CustomerEntity c
        JOIN FETCH c.account ac
        WHERE ac.accountId = :accountId
        """)
    Optional<CustomerEntity> findByAccountId(@Param("accountId")Integer accountId);

    @Query("""
        SELECT cus.cart FROM CustomerEntity cus
        WHERE cus.customerId = ?1
        """)
    Optional<CartEntity> findCartByCustomerId(Integer customerId);

    @Modifying
    @Query(value = """
        UPDATE CustomerEntity cus
        SET cus.cart = :cart
        WHERE cus.customerId = :customerId
        """)
    void addCartToCustomer(@Param("cart") CartEntity cart, @Param("customerId") Integer customerId);
    @Query(value = """
        DELETE FROM customer c
        WHERE c.account_id = :accountId
        """, nativeQuery = true)
    @Modifying(clearAutomatically = true)
    void deleteByAccountId(@Param("accountId") Integer accountId);
}
