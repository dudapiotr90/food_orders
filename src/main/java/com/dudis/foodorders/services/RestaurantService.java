package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.DeliveryAddressesDTO;
import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.api.dtos.OrdersDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.DeliveryAddressMapper;
import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.api.mappers.OrderMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final DeliveryAddressService deliveryAddressService;
    private final OrderService orderService;
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
            .orElseThrow(() -> new NotFoundException("Restaurant with id: [%s] doesn't have a menu".formatted(restaurantId)));
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
}
