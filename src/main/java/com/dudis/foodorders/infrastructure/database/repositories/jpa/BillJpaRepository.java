package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface BillJpaRepository extends JpaRepository<BillEntity,Integer> {

    @Query("""
        SELECT be FROM BillEntity be
        JOIN FETCH be.owner ow
        WHERE ow.ownerId = :ownerId and be.payed = :payed
        """)
    List<BillEntity> findByOwnerIdAndPayed(@Param("ownerId") Integer ownerId,@Param("payed") boolean payed);

    @Query("""
        SELECT be FROM BillEntity be
        JOIN FETCH be.customer cus
        WHERE cus.customerId = :customerId and be.payed = :payed
        """)
    List<BillEntity> findByCustomerIdAndPayed(@Param("customerId") Integer ownerId,@Param("payed") boolean payed);
}
