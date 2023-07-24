package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.api.mappers.DeliveryAddressMapper;
import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.api.mappers.OrderMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final DeliveryAddressService deliveryAddressService;
    private final OrderService orderService;
    private final MenuService menuService;
    private final RestaurantMapper restaurantMapper;
    private final MenuMapper menuMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final OrderMapper orderMapper;
    public List<Restaurant> findOwnersLocals(Integer ownerId) {
        return restaurantDAO.findRestaurantsWhereOwnerId(ownerId);
    }

    public void addLocal(Restaurant restaurant) {
        restaurantDAO.addLocal(restaurant);
    }

//    @Transactional
    public RestaurantDTO findProcessingRestaurant(Integer restaurantId) {
        return restaurantDAO.findProcessingRestaurant(restaurantId)
            .map(restaurantMapper::mapToDTO).orElseThrow(
                () -> new NotFoundException(
                    String.format("Couldn't find restaurant with id: [%s]",restaurantId)
                )
            );
    }

    public MenuDTO getCurrentMenu(Integer restaurantId) {
        return restaurantDAO.getMenu(restaurantId)
            .map(menuMapper::mapToDTO)
            .orElse(MenuDTO.builder().foods(Set.of()).build());
//            .orElseThrow(() -> new NotFoundException("Restaurant with id: [%s] doesn't have a menu".formatted(restaurantId)));
    }

    public DeliveryAddressesDTO getDeliveryAddresses(Integer restaurantId) {
        return DeliveryAddressesDTO.builder()
            .deliveries(deliveryAddressService.getRestaurantDeliveryAddresses(restaurantId).stream()
                .map(deliveryAddressMapper::mapToDTO)
                .toList())
            .build();
    }

    public OrdersDTO findOrders(Integer restaurantId) {
        return OrdersDTO.builder()
            .orders(orderService.getRestaurantOrders(restaurantId).stream()
            .map(orderMapper::mapToDTO)
            .toList())
            .build();
    }

    @Transactional
    public void addFoodToMenu(FoodDTO foodDTO, Integer restaurantId) {
        Optional<Menu> menu = restaurantDAO.getMenu(restaurantId);
        if (menu.isPresent()) {
            menuService.addFoodToMenu(menuMapper.mapFoodFromDTO(foodDTO),menu.get());
        } else {
            throw new NotFoundException("Menu doesn't exist");
        }
    }
}
