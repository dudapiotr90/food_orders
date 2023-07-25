package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.services.dao.DeliveryAddressDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressDAO deliveryAddressDAO;
    public List<DeliveryAddress> getRestaurantDeliveryAddresses(Integer restaurantId) {
        return deliveryAddressDAO.getRestaurantDeliveryAddresses(restaurantId);
    }

    public void addDeliveryAddressToRestaurant(DeliveryAddress deliveryAddress, Restaurant restaurant) {
        deliveryAddressDAO.addDeliveryAddressToRestaurant(deliveryAddress, restaurant);
    }
}
