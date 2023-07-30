package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Delivery;
import com.dudis.foodorders.domain.Restaurant;

import java.util.List;

public interface DeliveryDAO {

    List<Delivery> findPendingDeliveries(Integer accountId, boolean delivered);

    Integer countPendingDeliveriesForRestaurant(Restaurant restaurant, boolean delivered);
}
