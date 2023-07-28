package com.dudis.foodorders.infrastructure.security.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

@Repository
public interface ApiRoleJpaRepository extends JpaRepository<ApiRoleEntity,Integer> {

    ApiRoleEntity findFirstByRole(String role);

    @Query("""
        SELECT a.apiRoleId FROM ApiRoleEntity a
        WHERE a.role = :role
        """)
    Integer findByRole(@Param("role") String role);

    @Query("""
        SELECT a.role FROM ApiRoleEntity a
        WHERE a.apiRoleId = :roleId
        """)
    String findRoleByApiRoleId(@Param("roleId") Integer roleId);
}