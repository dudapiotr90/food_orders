package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity,Integer> {

    @Query("""
        SELECT re FROM RestaurantEntity re 
        JOIN FETCH re.owner ow
        WHERE ow.ownerId = :ownerId
        """)
    List<RestaurantEntity> findByOwnerId(@Param("ownerId") Integer ownerId);
}
