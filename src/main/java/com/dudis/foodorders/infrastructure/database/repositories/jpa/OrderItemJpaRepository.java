package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Integer> {


    @Query("""
        SELECT oie FROM OrderItemEntity oie
        JOIN FETCH oie.food f
        JOIN FETCH oie.cart c
        WHERE f.foodId = :foodId
        AND c.cartId = :cartId
        """)
    Optional<OrderItemEntity> findAllByFoodIdAndCartId(@Param("foodId") Integer foodId, @Param("cartId") Integer cartId);

    @Query("""
        DELETE FROM oie OrderItemEntity oie
        JOIN FETCH oie.cart c
        WHERE oie.orderItemId = ?1
        AND c.cartId = ?2
        """)
    void deleteOrderItemFromCart(Integer orderItemId, Integer cartId);
}
