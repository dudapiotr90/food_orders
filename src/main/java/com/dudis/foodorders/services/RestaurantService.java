package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.api.mappers.*;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.domain.exception.SearchingException;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final DeliveryAddressService deliveryAddressService;
    private final BillService billService;
    private final OrderService orderService;
    private final FoodService foodService;
    private final StorageService storageService;
    private final PageableService pageableService;
    private final RestaurantMapper restaurantMapper;
    private final MenuMapper menuMapper;
    private final FoodMapper foodMapper;
    private final DeliveryAddressMapper deliveryAddressMapper;
    private final OrderMapper orderMapper;

    public List<Restaurant> findOwnerLocals(Integer ownerId) {
        return restaurantDAO.findRestaurantsWhereOwnerId(ownerId);
    }

    public void addLocal(Restaurant restaurant) {
        restaurantDAO.addLocal(restaurant);
    }

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
    }

    public List<Integer> findAllOwnerPendingOrders(Integer ownerId, List<RestaurantDTO> ownersRestaurants) {
        List<Restaurant> restaurants = ownersRestaurants.stream()
            .map(restaurantMapper::mapFromDTO).toList();
        return restaurants.stream()
            .map(this::countPendingOrdersForRestaurant)
            .toList();
    }

    public DeliveryAddressesDTO getDeliveryAddresses(Integer restaurantId) {
        return DeliveryAddressesDTO.builder()
            .deliveries(deliveryAddressService.getRestaurantDeliveryAddresses(restaurantId).stream()
                .map(deliveryAddressMapper::mapToDTO)
                .toList())
            .build();
    }

    public OrdersDTO findOrdersByInProgress(Integer restaurantId, boolean nonIssued) {
        return OrdersDTO.builder()
            .orders(orderService.findOrdersByInProgress(restaurantId, nonIssued).stream()
                .map(orderMapper::mapToDTO)
                .toList())
            .build();
    }

    public OrdersDTO findPayedOrdersAndNotRealized(Integer restaurantId) {
        List<Order> ordersNotInProgressAndPayed = billService
            .findOrdersNotInProgressAndPayedAndNotRealized(restaurantId, false, true, false);

        return OrdersDTO.builder()
            .orders(ordersNotInProgressAndPayed.stream()
                .map(orderMapper::mapToDTO)
                .toList())
            .build();
    }

    @Transactional
    public String addFoodToMenu(FoodDTO foodDTO, Integer restaurantId, MultipartFile image) throws FileUploadException {
        Menu menu = restaurantDAO.getMenu(restaurantId).orElseThrow(() -> new NotFoundException("Menu doesn't exist"));
        Food food = foodMapper.mapFromDTO(foodDTO);
        String foodImage = "";
        try {
            foodImage = storageService.uploadImageToServer(image, restaurantId);
        } catch (IOException e) {
            log.error("Failed to upload an image: [{}]",image.getOriginalFilename(),e);
            throw new FileUploadException("Failed to upload an image: [%s]".formatted(image.getOriginalFilename()));
        }
        if (image.isEmpty()) {
            foodService.addFoodToMenu(food, menu, null);
        } else {
            foodService.addFoodToMenu(food, menu, foodImage);
        }
        return foodImage.isBlank() ? "No image" : foodImage;
    }

    @Transactional
    public String updateMenuPosition(FoodDTO foodDTO, Integer restaurantId, MultipartFile image) throws FileUploadException {
        Food food = foodMapper.mapFromDTO(foodDTO);
        String foodImagePath = "";
        try {
            foodImagePath = storageService.uploadImageToServer(image, restaurantId);
        } catch (IOException e) {
            log.error("Failed to upload an image: [{}]",image.getOriginalFilename(),e);
            throw new FileUploadException("Failed to upload an image: [%s]".formatted(image.getOriginalFilename()));
        }
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

    public Page<DeliveryAddressDTO> getPaginatedDeliveryAddresses(
        Integer deliveryPageNumber,
        Integer pageSize,
        String sortBy,
        String sortHow,
        Integer restaurantId
    ) {
        Pageable pageable = pageableService.preparePageable(deliveryPageNumber, pageSize, sortHow, sortBy);
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

    public Optional<Owner> findOwnerByRestaurant(Restaurant restaurant) {
        return restaurantDAO.findOwnerByRestaurant(restaurant);
    }

    @Transactional
    public void realizeOrder(String orderNumber, Integer restaurantId) {
        Restaurant restaurant = restaurantDAO.findProcessingRestaurant(restaurantId)
            .orElseThrow(() -> new NotFoundException("Can't realize order for non existing restaurant"));
        orderService.realizeOrder(orderNumber, restaurant);
    }

    public Page<RestaurantForCustomerDTO> findAllRestaurants(
        Integer pageNumber,
        Integer pageSize,
        String sortBy,
        String sortHow
    ) {
        Pageable pageable = pageableService.preparePageable(pageNumber, pageSize, sortHow, sortBy);
        return restaurantDAO.findAllRestaurants(pageable)
            .map(restaurantMapper::mapToDTOForCustomer);
    }

    public Page<RestaurantForCustomerDTO> findAllRestaurantsByParameters(
        DeliveryAddressDTO deliveryAddress,
        Integer pageNumber,
        Integer pageSize,
        String sortBy,
        String sortHow
    ) {
        if ((!deliveryAddress.getPostalCode().isBlank() || !deliveryAddress.getStreet().isBlank())
            && deliveryAddress.getCity().isBlank()) {
            throw new SearchingException("""
                City is missing.
                You can search by city or with every detail.
                You can also search without any input
                """);
        } else if (!deliveryAddress.getCity().isBlank() &&
            (deliveryAddress.getPostalCode().isBlank() || deliveryAddress.getStreet().isBlank())) {
            return findAllRestaurantsByCity(deliveryAddress, pageNumber, pageSize, sortBy, sortHow);
        } else {
            return findAllRestaurantsByCityPostalCodeStreet(deliveryAddress, pageNumber, pageSize, sortBy, sortHow);
        }

    }

    private Page<RestaurantForCustomerDTO> findAllRestaurantsByCity(
        DeliveryAddressDTO deliveryAddress,
        Integer pageNumber,
        Integer pageSize,
        String sortBy,
        String sortHow
    ) {
        Pageable pageable = pageableService.preparePageable(pageNumber, pageSize, sortHow, sortBy);
        return restaurantDAO.findAllRestaurantsByCity(deliveryAddress.getCity(),pageable)
            .map(restaurantMapper::mapToDTOForCustomer);

    }

    private Page<RestaurantForCustomerDTO> findAllRestaurantsByCityPostalCodeStreet(
        DeliveryAddressDTO deliveryAddress,
        Integer pageNumber,
        Integer pageSize,
        String sortBy,
        String sortHow
    ) {
        Pageable pageable = pageableService.preparePageable(pageNumber, pageSize, sortHow, sortBy);
        return restaurantDAO.findAllRestaurantsByFullAddress(
            deliveryAddress.getCity(),deliveryAddress.getPostalCode(),deliveryAddress.getStreet(),pageable)
            .map(restaurantMapper::mapToDTOForCustomer);
    }

    private Integer countPendingOrdersForRestaurant(Restaurant restaurant) {
        return orderService.countPendingOrdersForRestaurant(restaurant);
    }
}

