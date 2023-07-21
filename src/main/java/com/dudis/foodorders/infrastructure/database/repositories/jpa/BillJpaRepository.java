package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;

import java.util.List;

@Repository
public interface BillJpaRepository extends JpaRepository<BillEntity,Integer> {
    List<BillEntity> findByOwnerIdAndPayed(Integer accountId, boolean payed);
}
