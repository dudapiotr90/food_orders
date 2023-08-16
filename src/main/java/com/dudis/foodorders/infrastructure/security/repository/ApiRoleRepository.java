package com.dudis.foodorders.infrastructure.security.repository;

import com.dudis.foodorders.infrastructure.database.mappers.ApiRoleEntityMapper;
import com.dudis.foodorders.infrastructure.security.ApiRole;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ApiRoleJpaRepository;
import com.dudis.foodorders.services.dao.ApiRoleDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ApiRoleRepository implements ApiRoleDAO {
    private final ApiRoleJpaRepository apiRoleJpaRepository;
    private final ApiRoleEntityMapper apiRoleEntityMapper;

    @Override
    public String findRoleById(Integer roleId) {
        return apiRoleJpaRepository.findRoleByApiRoleId(roleId);
    }

    @Override
    public List<ApiRole> findApiRoles() {
        return apiRoleJpaRepository.findAll().stream()
            .map(apiRoleEntityMapper::mapFromEntity).toList();
    }
}
