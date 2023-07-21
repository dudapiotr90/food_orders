package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryEntity;

import java.util.List;

@Repository
public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity,Integer> {
    List<DeliveryEntity> findByOwnerIdAndDelivered(Integer accountId, Boolean delivered);
}
