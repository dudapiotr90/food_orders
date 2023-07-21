package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.LocalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalJpaRepository extends JpaRepository<LocalEntity,Integer> {

    List<LocalEntity> findAllByOwnerId(Integer accountId);
}
