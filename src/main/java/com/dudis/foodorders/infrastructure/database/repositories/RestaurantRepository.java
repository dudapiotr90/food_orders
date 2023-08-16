package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OwnerEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.RestaurantJpaRepository;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final MenuEntityMapper menuEntityMapper;
    private final OwnerEntityMapper ownerEntityMapper;
    @Override
    public List<Restaurant> findRestaurantsWhereOwnerId(Integer ownerId) {
        return restaurantJpaRepository.findByOwnerId(ownerId).stream()
            .map(restaurantEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public void addLocal(Restaurant restaurant) {
        restaurantJpaRepository.save(restaurantEntityMapper.mapToEntity(restaurant));
    }

    @Override
    public Optional<Restaurant> findProcessingRestaurant(Integer restaurantId) {
        return restaurantJpaRepository.findById(restaurantId)
            .map(restaurantEntityMapper::mapFromEntity);
    }

    @Override
    public Optional<Menu> getMenu(Integer restaurantId) {
        return restaurantJpaRepository.findMenuByRestaurantId(restaurantId)
            .map(menuEntityMapper::mapFromEntity);
    }
//
//    @Override
//    public Page<Menu> getPaginatedMenu(Integer restaurantId, Pageable pageable) {
//        restaurantJpaRepository.findMenuByRestaurantId(restaurantId)
//            .orElseThrow(() -> new EntityNotFoundException("Restaurant doesn't have a menu"));
//
//        return restaurantJpaRepository.findPaginatedMenuByRestaurantId(restaurantId,pageable)
//            .map(menuEntityMapper::mapFromEntity);
//    }

    @Override
    public Restaurant findRestaurantByMenu(Menu menu) {
        MenuEntity menuEntity = menuEntityMapper.mapToEntity(menu);
        RestaurantEntity restaurant = restaurantJpaRepository.findByMenu(menuEntity);
        return restaurantEntityMapper.mapFromEntity(restaurant);
    }

    @Override
    public Optional<Owner> findOwnerByRestaurant(Restaurant restaurant) {
        Optional<OwnerEntity> owner = restaurantJpaRepository.findOwnerByRestaurantId(restaurant.getRestaurantId());
        return owner.map(ownerEntityMapper::mapFromEntity);
    }

    @Override
    public Page<Restaurant> findAllRestaurants(Pageable pageable) {
        return restaurantJpaRepository.findAll(pageable)
            .map(restaurantEntityMapper::mapFromEntity);
    }

    @Override
    public Page<Restaurant> findAllRestaurantsByCity(String city, Pageable pageable) {
        Page<Object[]> restaurantDetails = restaurantJpaRepository.findAllRestaurantsByCity(city, pageable);
        return restaurantDetails
            .map(restaurantEntityMapper::buildRestaurantFromObject);
    }

    @Override
    public Page<Restaurant> findAllRestaurantsByFullAddress(String city, String postalCode, String street, Pageable pageable) {
        Page<Object[]> restaurantDetails = restaurantJpaRepository.findAllRestaurantsByFullAddress(
            city, postalCode,street, pageable);
        return restaurantDetails
            .map(restaurantEntityMapper::buildRestaurantFromObject);
    }


}
