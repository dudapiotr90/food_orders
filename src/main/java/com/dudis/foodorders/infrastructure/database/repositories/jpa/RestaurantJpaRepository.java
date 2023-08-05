package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Integer> {

    @Query("""
        SELECT re FROM RestaurantEntity re
        JOIN FETCH re.owner ow
        WHERE ow.ownerId = :ownerId
        """)
    List<RestaurantEntity> findByOwnerId(@Param("ownerId") Integer ownerId);


    @Query("""
        SELECT re.menu FROM RestaurantEntity re
        WHERE re.restaurantId = :restaurantId
        """)
    Optional<MenuEntity> findMenuByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("""
        SELECT re.menu FROM RestaurantEntity re
        WHERE re.restaurantId = :restaurantId
        """)
    Page<MenuEntity> findPaginatedMenuByRestaurantId(@Param("restaurantId") Integer restaurantId, Pageable pageable);

    RestaurantEntity findByMenu(MenuEntity menu);

    @Query("""
        SELECT r.owner FROM RestaurantEntity r
        WHERE r.restaurantId = ?1
        """)
    Optional<OwnerEntity> findOwnerByRestaurantId(Integer restaurantId);
}
