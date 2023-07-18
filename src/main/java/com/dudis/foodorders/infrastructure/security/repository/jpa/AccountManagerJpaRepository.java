package com.dudis.foodorders.infrastructure.security.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.security.entity.AccountManagerEntity;

@Repository
public interface AccountManagerJpaRepository extends JpaRepository<AccountManagerEntity,Integer> {
}
