package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("""
        UPDATE OrderItemEntity oie
        SET oie.cart = null
        WHERE oie.orderItemId = ?1
        """)
    void deleteOrderItemFromCart(Integer orderItemId);

    @Modifying
    @Query(value = """
        UPDATE order_item
        SET order_id = ?2
        WHERE order_item_id = ?1
        """,nativeQuery = true)
    void setOrder(Integer orderItemId, Integer orderId);

    @Modifying
    @Query(value = """
        UPDATE order_item
        SET cart_id = ?2
        WHERE order_item_id = ?1
        """,nativeQuery = true)
    void setCart(Integer orderItemId, Integer cartId);

    @Modifying
    @Query(value = """
        UPDATE order_item
        SET order_id = null
        WHERE order_item_id = ?1
        """,nativeQuery = true)
    void setOrderNull(Integer orderItemId);

}
