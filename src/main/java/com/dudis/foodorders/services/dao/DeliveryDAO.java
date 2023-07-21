package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Delivery;

import java.util.List;

public interface DeliveryDAO {

    List<Delivery> findPendingDeliveries(Integer accountId, boolean delivered);
}
