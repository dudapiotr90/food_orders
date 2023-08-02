package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodJpaRepository extends JpaRepository<FoodEntity, Integer> {

    @Query(value = """
        SELECT f FROM FoodEntity f
        JOIN FETCH f.menu m
        WHERE m.menuId = :menuId
        """
    )
    Page<FoodEntity> findByMenuId(@Param("menuId") Integer menuId, Pageable pageable);

    @Query(value = """
        SELECT f.menu FROM FoodEntity f
        WHERE f.foodId = :foodId
        """
    )
    MenuEntity findMenuByFoodId(@Param("foodId") Integer foodId);

    @Query(value = """
        SELECT f FROM FoodEntity f
        JOIN FETCH f.menu m
        WHERE m.menuId = :menuId
        """
    )
    List<FoodEntity> findByMenuId(@Param("menuId") Integer menuId);
}
