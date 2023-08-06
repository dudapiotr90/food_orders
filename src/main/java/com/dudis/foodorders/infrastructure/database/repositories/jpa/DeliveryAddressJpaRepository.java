package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.DeliveryAddressEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryAddressJpaRepository extends JpaRepository<DeliveryAddressEntity, Integer> {


    @Query("""
        SELECT del FROM DeliveryAddressEntity del
        JOIN FETCH del.restaurant res
        WHERE res.restaurantId = :restaurantId
        """)
    List<DeliveryAddressEntity> findDeliveryAddressesByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("""
        SELECT del FROM DeliveryAddressEntity del
        JOIN FETCH del.restaurant res
        WHERE res.restaurantId = :restaurantId
        """)
    Page<DeliveryAddressEntity> findPaginatedDeliveryAddressesByRestaurantId(@Param("restaurantId") Integer restaurantId, Pageable pageable);

    Integer countByRestaurant(RestaurantEntity restaurant);

    @Query(value = """
        SELECT DISTINCT res.restaurant_id, res.name, res.description, res.local_type, res.menu_id
        FROM delivery_address da
        JOIN restaurant res ON da.restaurant_id = res.restaurant_id
        WHERE da.city = :city
        AND da.postal_code = :postalCode
        AND da.street LIKE :street
         """,nativeQuery = true)
    List<Object[]> findRestaurantsWithAddress(@Param("city") String city, @Param("postalCode") String postalCode, @Param("street")String street);


}
