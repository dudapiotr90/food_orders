package com.dudis.foodorders.infrastructure.security.repository;

import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountManagerJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AccountManagerRepository {

    private final AccountManagerJpaRepository accountManagerJpaRepository;
}
