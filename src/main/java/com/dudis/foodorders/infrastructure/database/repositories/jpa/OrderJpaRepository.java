package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity,Integer> {
    @Query("""
        SELECT o FROM OrderEntity o
        JOIN FETCH o.restaurant res
        WHERE res.restaurantId = :restaurantId
        """)
    List<OrderEntity> findOrdersByRestaurantId(Integer restaurantId);
}
