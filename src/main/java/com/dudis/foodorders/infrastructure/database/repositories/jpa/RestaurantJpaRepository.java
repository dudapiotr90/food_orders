package com.dudis.foodorders.infrastructure.database.repositories.jpa;

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
        SELECT re FROM RestaurantEntity re
        JOIN FETCH re.owner ow
        WHERE ow.ownerId = :ownerId
        """)
    Page<RestaurantEntity> findByOwnerId(Integer ownerId, Pageable pageable);


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

    @Query(value = """
        SELECT DISTINCT res.restaurant_id, res.name, res.description, res.type, res.menu_id
        FROM restaurant res
        JOIN (
            SELECT DISTINCT restaurant_id
            FROM delivery_address da
            WHERE da.city LIKE %:city%
        ) da ON res.restaurant_id = da.restaurant_id
        """,nativeQuery = true)
    Page<Object[]>  findAllRestaurantsByCity(@Param("city")String city, Pageable pageable);

    @Query(value = """
        SELECT DISTINCT res.restaurant_id, res.name, res.description, res.type, res.menu_id
        FROM restaurant res
        JOIN (
            SELECT DISTINCT restaurant_id
            FROM delivery_address da
            WHERE da.city = :city
            AND da.postal_code = :postalCode
            AND da.street LIKE %:street%
        ) da ON res.restaurant_id = da.restaurant_id
         """,nativeQuery = true)
    Page<Object[]> findAllRestaurantsByFullAddress(String city, String postalCode, String street, Pageable pageable);
}
