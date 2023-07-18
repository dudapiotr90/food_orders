package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryEntity;

@Repository
public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity,Integer> {
}
