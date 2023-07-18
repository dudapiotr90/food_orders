package com.dudis.foodorders.infrastructure.security.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

@Repository
public interface ApiRoleJpaRepository extends JpaRepository<ApiRoleEntity,Integer> {

    ApiRoleEntity findFirstByRole(String role);
}
