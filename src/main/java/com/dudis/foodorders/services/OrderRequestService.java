package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderDetailsDTO;
import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.api.mappers.FoodMapper;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.domain.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderRequestService {

    private final MenuService menuService;
    private final RestaurantMapper restaurantMapper;
    private final OrderItemMapper orderItemMapper;
    private final FoodMapper foodMapper;

    public List<OrderDetailsDTO> prepareOrderRequests(Cart cart, Set<Restaurant> restaurants) {
        return restaurants.stream()
            .map(r -> OrderDetailsDTO.builder()
                .restaurant(restaurantMapper.mapToDTOForCustomer(r))
                .orderItems(cart.getOrderItems().stream()
                    .map(orderItemMapper::mapToDTO)
                    .filter(oi -> menuService.menuContainsFood(foodMapper.mapFromDTO(oi.getFood()),r.getMenu()))
                    .map(oi -> oi.withTotalCost(oi.getQuantity().multiply(oi.getFood().getPrice())))
                    .toList())
                .build())
            .toList();
    }
}
