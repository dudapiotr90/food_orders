package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.BillEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillJpaRepository extends JpaRepository<BillEntity, Integer> {

    @Query("""
        SELECT be FROM BillEntity be
        JOIN FETCH be.owner ow
        WHERE ow.ownerId = :ownerId and be.payed = :payed
        """)
    List<BillEntity> findByOwnerIdAndPayed(@Param("ownerId") Integer ownerId, @Param("payed") boolean payed);

    @Query("""
        SELECT be FROM BillEntity be
        JOIN FETCH be.customer cus
        WHERE cus.customerId = :customerId and be.payed = :payed
        """)
    List<BillEntity> findByCustomerIdAndPayed(@Param("customerId") Integer ownerId, @Param("payed") boolean payed);

    @Query("""
        SELECT be.billNumber FROM BillEntity be
        JOIN be.order o
        WHERE o.orderNumber = :orderNumber
        """)
    String findByOrderNumber(@Param("orderNumber") String orderNumber);

    @Modifying
    @Query("""
        UPDATE BillEntity be
        SET be.payed = :payed
        WHERE be.billNumber = :billNumber
        """)
    void setPayedAsTrue(@Param("billNumber") String billNumber, @Param("payed") boolean payed);

    BillEntity findByBillNumber(String billNumber);

    @Query("""
        SELECT be.order FROM BillEntity be
        JOIN be.order o
        JOIN FETCH o.restaurant r
        WHERE r.restaurantId = :restaurantId
        AND o.inProgress = :inProgress
        AND be.payed = :payed
        AND o.realized = :realized
        """)
    List<OrderEntity> findOrdersNotInProgressAndPayedAndNotRealized(
        @Param("restaurantId") Integer restaurantId,
        @Param("inProgress") boolean inProgress,
        @Param("payed") boolean payed,
        @Param("realized")boolean realized
    );
}
