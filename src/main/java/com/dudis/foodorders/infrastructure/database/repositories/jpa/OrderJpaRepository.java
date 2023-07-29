package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity,Integer> {
    @Query("""
        SELECT o FROM OrderEntity o
        JOIN FETCH o.restaurant res
        WHERE res.restaurantId = :restaurantId
        """)
    List<OrderEntity> findOrdersByRestaurantId(Integer restaurantId);

    @Query("""
        SELECT COUNT(o)
        FROM OrderEntity o
        WHERE o.realized = :realized
        AND o.restaurant = :restaurantId
        """)
    Integer countByRestaurantIdAndRealized(@Param("restaurantId") RestaurantEntity restaurantId, @Param("realized") boolean realized);
}
