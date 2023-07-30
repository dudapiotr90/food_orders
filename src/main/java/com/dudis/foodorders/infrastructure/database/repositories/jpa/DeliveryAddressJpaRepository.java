package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryAddressEntity;

import java.util.List;

@Repository
public interface DeliveryAddressJpaRepository extends JpaRepository<DeliveryAddressEntity,Integer> {


    @Query("""
        SELECT del FROM DeliveryAddressEntity del
        JOIN FETCH del.restaurant res
        WHERE res.restaurantId = :restaurantId
        """)
    List<DeliveryAddressEntity> findDeliveryAddressesByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("""
        SELECT del FROM DeliveryAddressEntity del
        JOIN FETCH del.restaurant res
        WHERE res.restaurantId = :restaurantId
        """)
    Page<DeliveryAddressEntity> findPaginatedDeliveryAddressesByRestaurantId(@Param("restaurantId") Integer restaurantId, Pageable pageable);

    Integer countByRestaurant(RestaurantEntity restaurant);
}
