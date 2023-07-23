package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;

import java.util.Optional;

@Repository
public interface MenuJpaRepository extends JpaRepository<MenuEntity,Integer> {

//    @Query("""
//        SELECT m FROM MenuEntity m
//        JOIN FETCH m.
//        """)
//    Optional<MenuEntity> findMenuByRestaurantId(Integer restaurantId);
}
