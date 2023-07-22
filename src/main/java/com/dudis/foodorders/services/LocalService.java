package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocalService {

    private final RestaurantDAO restaurantDAO;
    public List<Restaurant> findOwnersLocals(Integer accountId) {
        return restaurantDAO.findLocalsWhereOwnerId(accountId);
    }

    public void addLocal(Restaurant restaurant) {
        restaurantDAO.addLocal(restaurant);
    }
}
