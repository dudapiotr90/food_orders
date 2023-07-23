package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.infrastructure.database.mappers.DeliveryAddressEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.DeliveryAddressJpaRepository;
import com.dudis.foodorders.services.dao.DeliveryAddressDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DeliveryAddressRepository implements DeliveryAddressDAO {

    private final DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper;

    @Override
    public List<DeliveryAddress> getRestaurantDeliveryAddresses(Integer restaurantId) {
        return deliveryAddressJpaRepository.findDeliveryAddressesByRestaurantId(restaurantId).stream()
            .map(deliveryAddressEntityMapper::mapFromEntity)
            .toList();
    }
}
