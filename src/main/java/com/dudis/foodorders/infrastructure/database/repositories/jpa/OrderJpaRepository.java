package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Integer> {
    @Query("""
        SELECT o FROM OrderEntity o
        JOIN FETCH o.restaurant res
        WHERE res.restaurantId = :restaurantId
        AND o.inProgress = :inProgress
        """)
    List<OrderEntity> findRestaurantOrdersInProgress(@Param("restaurantId") Integer restaurantId, @Param("inProgress") boolean inProgress);

    @Query("""
        SELECT COUNT(o)
        FROM OrderEntity o
        WHERE o.realized = :realized
        AND o.restaurant = :restaurantId
        """)
    Integer countByRestaurantIdAndRealized(@Param("restaurantId") RestaurantEntity restaurantId, @Param("realized") boolean realized);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);


    void deleteByOrderNumber(String orderNumber);

    @Query("""
        SELECT o FROM OrderEntity o
        JOIN FETCH o.customer cus
        WHERE cus.customerId = :customerId
        """)
    List<OrderEntity> findCancelableOrders(@Param("customerId") Integer customerId);

    @Modifying
    @Query("""
        UPDATE OrderEntity oe
        SET oe.inProgress = ?1
        WHERE oe.orderNumber = ?2
        """)
    void setInProgress(Boolean inProgress, String orderNumber);

    @Modifying
    @Query("""
        UPDATE OrderEntity oe
        SET oe.realized = ?2,
            oe.completedDateTime = ?3
        WHERE oe.orderNumber = ?1
        """)
    void updateRealizedAndCompletedDateTime(String orderNumber, Boolean realized, OffsetDateTime completedDateTime);

    @Query("""
        SELECT o FROM OrderEntity o
        JOIN FETCH o.restaurant res
        JOIN FETCH o.customer cus
        WHERE res.restaurantId IN (:restaurantIds)
        AND o.realized = :realized
        """)
    Page<OrderEntity> findByRestaurantIds(
        @Param("restaurantIds") List<Integer> restaurantIds,
        @Param("realized") boolean realized,
        Pageable pageable);

    @Query(value = """
        SELECT res FROM food_order o
        JOIN restaurant res on o.restaurant_id = res.restaurant_id
        WHERE o.order_number = ?1
        """,nativeQuery = true)
    List<Object[]> findRestaurantByOrderNumber(String orderNumber);

    @Query("""
        SELECT o FROM OrderEntity o
        JOIN FETCH o.restaurant res
        JOIN FETCH o.customer cus
        WHERE cus.customerId = :customerId
        AND o.realized = :realized
        """)
    Page<OrderEntity> findByCustomerIdAndRealized(Integer customerId, boolean realized, Pageable pageable);
}
