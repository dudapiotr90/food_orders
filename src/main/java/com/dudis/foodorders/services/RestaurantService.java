package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.api.mappers.DeliveryAddressMapper;
import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.api.mappers.OrderMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final FoodService foodService;
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
                    String.format("Couldn't find restaurant with id: [%s]", restaurantId)
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
            foodService.addFoodToMenu(foodDTO, menu.get());
        } else {
            throw new NotFoundException("Menu doesn't exist");
        }
    }

    public Page<FoodDTO> getPaginatedMenu(int pageNumber, int pageSize, String sortBy, String sortHow, Integer restaurantId) {
        Sort sort = sortHow.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Menu menu = restaurantDAO.getMenu(restaurantId)
            .orElseThrow(() -> new EntityNotFoundException("Restaurant doesn't have a menu"));

        return foodService.getPaginatedFoods(menu.getMenuId(), pageable);

//        return restaurantDAO.getPaginatedMenu(restaurantId,pageable)
//            .map(menuMapper::mapToDTO);
    }

    @Transactional
    public void addDeliveryAddress(DeliveryAddressDTO deliveryAddressDTO, Integer restaurantId) {
        DeliveryAddress deliveryAddress = deliveryAddressMapper.mapFromDTO(deliveryAddressDTO);
        Restaurant restaurant = restaurantMapper.mapFromDTO(findProcessingRestaurant(restaurantId));
        deliveryAddressService.addDeliveryAddressToRestaurant(deliveryAddress, restaurant);
    }
}

