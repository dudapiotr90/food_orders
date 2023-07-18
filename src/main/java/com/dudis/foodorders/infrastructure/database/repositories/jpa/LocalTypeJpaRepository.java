package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.LocalTypeEntity;

@Repository
public interface LocalTypeJpaRepository extends JpaRepository<LocalTypeEntity,Integer> {
}
