package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.api.mappers.DeliveryAddressMapper;
import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.services.dao.DeliveryAddressDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressDAO deliveryAddressDAO;
    private final DeliveryAddressMapper deliveryAddressMapper;
    public List<DeliveryAddress> getRestaurantDeliveryAddresses(Integer restaurantId) {
        return deliveryAddressDAO.getRestaurantDeliveryAddresses(restaurantId);
    }

    public void addDeliveryAddressToRestaurant(DeliveryAddress deliveryAddress, Restaurant restaurant) {
        deliveryAddressDAO.addDeliveryAddressToRestaurant(deliveryAddress, restaurant);
    }

    public Page<DeliveryAddressDTO> getPaginatedRestaurantDeliveryAddresses(Integer restaurantId, Pageable pageable) {
        return deliveryAddressDAO.getPaginatedRestaurantDeliveryAddresses(restaurantId, pageable)
            .map(deliveryAddressMapper::mapToDTO);
    }

    public Integer countDeliveryAddressesForRestaurant(Restaurant restaurant) {
        return deliveryAddressDAO.countDeliveryAddressesForRestaurant(restaurant);
    }

    public void deleteById(Integer deliveryId) {
        deliveryAddressDAO.deleteById(deliveryId);
    }
}
