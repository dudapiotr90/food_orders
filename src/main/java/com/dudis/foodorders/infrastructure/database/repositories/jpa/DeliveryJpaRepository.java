package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryEntity;

import java.util.List;

@Repository
public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity,Integer> {

    @Query("""
        SELECT de FROM DeliveryEntity de
        JOIN FETCH de.owner ow
        WHERE ow.ownerId = :accountId and de.delivered = :delivered
        """)
    List<DeliveryEntity> findByOwnerIdAndDelivered(@Param("accountId") Integer accountId,@Param("delivered") Boolean delivered);

    Integer countByRestaurantAndDelivered(RestaurantEntity restaurant, boolean delivered);
}
