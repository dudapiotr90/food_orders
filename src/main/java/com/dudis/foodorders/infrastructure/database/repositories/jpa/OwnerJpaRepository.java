package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;

import java.util.List;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity,Integer> {
}
