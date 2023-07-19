package com.dudis.foodorders.infrastructure.security.repository;

import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.services.dao.ApiRoleDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ApiRoleRepository implements ApiRoleDAO {
    private final ApiRoleJpaRepository apiRoleJpaRepository;
}
