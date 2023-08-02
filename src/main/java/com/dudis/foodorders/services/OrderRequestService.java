package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderRequestDTO;
import com.dudis.foodorders.api.mappers.FoodMapper;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderRequestService {

    private final MenuService menuService;
    private final RestaurantMapper restaurantMapper;
    private final OrderItemMapper orderItemMapper;
    private final FoodMapper foodMapper;

    public List<OrderRequestDTO> prepareOrderRequests(Cart cart, Set<Restaurant> restaurants) {
        return restaurants.stream()
            .map(r -> OrderRequestDTO.builder()
                .restaurant(restaurantMapper.mapToDTOForCustomer(r))
                .orderItems(cart.getOrderItems().stream()
                    .map(orderItemMapper::mapToDTO)
                    .map(oi -> oi.withTotalCost(oi.getQuantity().multiply(oi.getFood().getPrice())))
                    .filter(oi -> menuService.menuContainsFood(foodMapper.mapFromDTO(oi.getFood()),r.getMenu()))
                    .toList())
                .build())
            .toList();
    }
}
