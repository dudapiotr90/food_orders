package com.dudis.foodorders.infrastructure.security.repository.jpa;

import com.dudis.foodorders.infrastructure.security.entity.AccountManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountManagerJpaRepository extends JpaRepository<AccountManagerEntity,Integer> {
}
