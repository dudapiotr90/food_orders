package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.api.mappers.*;
import com.dudis.foodorders.domain.DeliveryAddress;
import com.dudis.foodorders.domain.Food;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final DeliveryAddressService deliveryAddressService;
    private final OrderService orderService;
    private final FoodService foodService;
    private final StorageService storageService;
    private final RestaurantMapper restaurantMapper;
    private final MenuMapper menuMapper;
    private final FoodMapper foodMapper;
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

    public List<Integer> findAllOwnerPendingOrders(Integer ownerId, List<RestaurantDTO> ownersRestaurants) {
        List<Restaurant> restaurants = ownersRestaurants.stream()
            .map(restaurantMapper::mapFromDTO).toList();
        return restaurants.stream()
            .map(this::countPendingOrdersForRestaurant)
            .toList();
    }

    private Integer countPendingOrdersForRestaurant(Restaurant restaurant) {
        return orderService.countPendingOrdersForRestaurant(restaurant);
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
    public String addFoodToMenu(FoodDTO foodDTO, Integer restaurantId, MultipartFile image) throws IOException {
        Menu menu = restaurantDAO.getMenu(restaurantId).orElseThrow(() -> new NotFoundException("Menu doesn't exist"));
        Food food = foodMapper.mapFromDTO(foodDTO);
        String foodImage = storageService.uploadImageToServer(image, restaurantId);
        if (image.isEmpty()) {
            foodService.addFoodToMenu(food, menu, null);
        } else {
            foodService.addFoodToMenu(food, menu, foodImage);
        }
        return foodImage.isBlank() ? "No image" : foodImage;
    }

    @Transactional
    public String updateMenuPosition(FoodDTO foodDTO, Integer restaurantId, MultipartFile image) throws IOException {
        Food food = foodMapper.mapFromDTO(foodDTO);
        String foodImagePath = storageService.uploadImageToServer(image, restaurantId);
        if (image.isEmpty()) {
            foodService.updateMenuPosition(food, null);
        } else {
            String imagePathToDelete = foodService.updateMenuPosition(food, foodImagePath);
            if (Objects.nonNull(imagePathToDelete)) {
                storageService.removeImageFromServer(imagePathToDelete);
            }
        }
        return foodImagePath.isBlank() ? "No image" : foodImagePath;
    }

    @Transactional
    public String deleteFoodFromMenu(Integer foodId) {
        String imagePathToDelete = foodService.deleteFood(foodId);
        if (Objects.nonNull(imagePathToDelete)) {
            storageService.removeImageFromServer(imagePathToDelete);
        }
        return Objects.isNull(imagePathToDelete) ? "Already removed" : "Removed Successfully";
    }

    public Page<FoodDTO> getPaginatedMenu(Integer pageNumber, Integer pageSize, String sortBy, String sortHow, Integer restaurantId) {
        if (Objects.isNull(pageNumber)) {
            pageNumber = 1;
        }
        Sort sort = sortHow.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Menu menu = restaurantDAO.getMenu(restaurantId)
            .orElseThrow(() -> new EntityNotFoundException("Restaurant doesn't have a menu"));

        return foodService.getPaginatedFoods(menu.getMenuId(), pageable);
    }

    public Page<DeliveryAddressDTO> getPaginatedDeliveries(Integer deliveryPageNumber, Integer pageSize, String sortBy, String sortHow, Integer restaurantId) {
        if (Objects.isNull(deliveryPageNumber)) {
            deliveryPageNumber = 1;
        }
        Sort sort = sortHow.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(deliveryPageNumber - 1, pageSize, sort);
        return deliveryAddressService.getPaginatedRestaurantDeliveryAddresses(restaurantId, pageable);
    }

    @Transactional
    public void addDeliveryAddress(DeliveryAddressDTO deliveryAddressDTO, Integer restaurantId) {
        DeliveryAddress deliveryAddress = deliveryAddressMapper.mapFromDTO(deliveryAddressDTO);
        Restaurant restaurant = restaurantMapper.mapFromDTO(findProcessingRestaurant(restaurantId));
        deliveryAddressService.addDeliveryAddressToRestaurant(deliveryAddress, restaurant);
    }

    public void deleteAddressFromRestaurant(Integer deliveryId) {
        deliveryAddressService.deleteById(deliveryId);

    }

    public Restaurant findRestaurantByMenu(Menu menu) {
        return restaurantDAO.findRestaurantByMenu(menu);
    }
}

