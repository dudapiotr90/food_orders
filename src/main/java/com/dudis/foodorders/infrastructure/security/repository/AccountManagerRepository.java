package com.dudis.foodorders.infrastructure.security.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountManagerJpaRepository;

@Repository
@AllArgsConstructor
public class AccountManagerRepository {

    private final AccountManagerJpaRepository accountManagerJpaRepository;
}
