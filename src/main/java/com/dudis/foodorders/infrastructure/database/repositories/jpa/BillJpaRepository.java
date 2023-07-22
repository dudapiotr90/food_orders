package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;

import java.util.List;

@Repository
public interface BillJpaRepository extends JpaRepository<BillEntity,Integer> {

    @Query("""
        SELECT be FROM BillEntity be
        JOIN FETCH be.owner ow
        WHERE ow.ownerId = :accountId and be.payed = :payed
        """)
    List<BillEntity> findByOwnerIdAndPayed(
    @Param("accountId") Integer accountId,
    @Param("payed") boolean payed
    );
}
