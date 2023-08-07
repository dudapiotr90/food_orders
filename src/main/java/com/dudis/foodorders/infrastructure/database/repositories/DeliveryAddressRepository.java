package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.DeliveryAddressEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import com.dudis.foodorders.infrastructure.database.mappers.DeliveryAddressEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.DeliveryAddressJpaRepository;
import com.dudis.foodorders.services.dao.DeliveryAddressDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DeliveryAddressRepository implements DeliveryAddressDAO {

    private final DeliveryAddressJpaRepository deliveryAddressJpaRepository;
    private final DeliveryAddressEntityMapper deliveryAddressEntityMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public List<DeliveryAddress> getRestaurantDeliveryAddresses(Integer restaurantId) {
        return deliveryAddressJpaRepository.findDeliveryAddressesByRestaurantId(restaurantId).stream()
            .map(deliveryAddressEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public void addDeliveryAddressToRestaurant(DeliveryAddress deliveryAddress, Restaurant restaurant) {
        DeliveryAddressEntity deliveryAddressEntity = deliveryAddressEntityMapper.mapToEntity(deliveryAddress);
        RestaurantEntity restaurantEntity = restaurantEntityMapper.mapToEntity(restaurant);
        deliveryAddressEntity.setRestaurant(restaurantEntity);
        deliveryAddressJpaRepository.save(deliveryAddressEntity);
    }

    @Override
    public Page<DeliveryAddress> getPaginatedRestaurantDeliveryAddresses(Integer restaurantId, Pageable pageable) {
        return deliveryAddressJpaRepository.findPaginatedDeliveryAddressesByRestaurantId(restaurantId, pageable)
            .map(deliveryAddressEntityMapper::mapFromEntity);
    }

    @Override
    public Integer countDeliveryAddressesForRestaurant(Restaurant restaurant) {
        return deliveryAddressJpaRepository.countByRestaurant(restaurantEntityMapper.mapToEntity(restaurant));
    }

    @Override
    public void deleteById(Integer deliveryId) {
        deliveryAddressJpaRepository.deleteById(deliveryId);
    }

    @Override
    public List<Restaurant> findRestaurantsWithAddress(String city, String postalCode, String street) {
        List<Object[]> restaurantDetails = deliveryAddressJpaRepository.findRestaurantsWithAddress(city, postalCode, street);
        return restaurantDetails.stream()
            .map(restaurantEntityMapper::buildRestaurantFromObject
            )
            .toList();
    }


}
