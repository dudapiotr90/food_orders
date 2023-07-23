package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryAddressEntity;

import java.util.List;

@Repository
public interface DeliveryAddressJpaRepository extends JpaRepository<DeliveryAddressEntity,Integer> {


    @Query("""
        SELECT del FORM DeliveryAddressEntity del
        JOIN FETCH del.restaurant res
        WHERE res.restaurantId = :restaurantId
        """)
    List<DeliveryAddressEntity> findDeliveryAddressesByRestaurantId(@Param("restaurantId") Integer restaurantId);
}
