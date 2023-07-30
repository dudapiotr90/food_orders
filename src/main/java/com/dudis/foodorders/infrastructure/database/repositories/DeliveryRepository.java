package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Delivery;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.mappers.DeliveryEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.DeliveryJpaRepository;
import com.dudis.foodorders.services.dao.DeliveryDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DeliveryRepository implements DeliveryDAO {

    private final DeliveryJpaRepository deliveryJpaRepository;
    private final DeliveryEntityMapper deliveryEntityMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public List<Delivery> findPendingDeliveries(Integer accountId, boolean delivered) {
        return deliveryJpaRepository.findByOwnerIdAndDelivered(accountId,delivered).stream()
            .map(deliveryEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public Integer countPendingDeliveriesForRestaurant(Restaurant restaurant, boolean delivered) {
        return deliveryJpaRepository.countByRestaurantAndDelivered(
            restaurantEntityMapper.mapToEntity(restaurant),
            delivered);
    }
}
