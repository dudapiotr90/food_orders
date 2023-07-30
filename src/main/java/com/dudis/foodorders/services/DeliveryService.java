package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Delivery;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.services.dao.DeliveryDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryService {

    private final DeliveryDAO deliveryDAO;

    public List<Delivery> findPendingDeliveries(Integer accountId, boolean delivered) {
        return deliveryDAO.findPendingDeliveries(accountId,delivered);
    }

    public Integer countPendingDeliveries(Restaurant restaurant, boolean delivered) {
        return deliveryDAO.countPendingDeliveriesForRestaurant(restaurant,delivered);
    }
}
