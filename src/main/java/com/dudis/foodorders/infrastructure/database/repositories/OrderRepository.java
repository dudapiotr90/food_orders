package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import com.dudis.foodorders.infrastructure.database.mappers.OrderEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OrderJpaRepository;
import com.dudis.foodorders.services.dao.OrderDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRepository implements OrderDAO {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public List<Order> findCancelableOrders(Integer customerId) {
        return orderJpaRepository.findCancelableOrders(customerId).stream()
            .filter(orderEntity -> orderEntity.getCancelTill().isAfter(OffsetDateTime.now()))
            .map(orderEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public List<Order> getRestaurantOrders(Integer restaurantId, boolean inProgress) {
        return orderJpaRepository.findRestaurantOrdersInProgress(restaurantId,inProgress).stream()
            .map(orderEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public Integer findPendingOrdersForRestaurant(Restaurant restaurantId) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.mapToEntity(restaurantId);
        return orderJpaRepository.countByRestaurantIdAndRealized(restaurantEntity, false);
    }

    @Override
    public Order issueAnOrder(Order order) {
        OrderEntity orderToSave = orderEntityMapper.mapToEntity(order);
        OrderEntity saved = orderJpaRepository.save(orderToSave);
        return orderEntityMapper.mapFromEntity(saved);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        Optional<OrderEntity> order = orderJpaRepository.findByOrderNumber(orderNumber);
        return order.map(orderEntityMapper::mapFromEntity);
    }

    @Override
    public void setOrderAsInProgress(Order order) {
        orderJpaRepository.setInProgress(order.getInProgress(),order.getOrderNumber());
    }

    @Override
    public void realizeOrder(Order order) {
        OrderEntity orderToRealize = orderEntityMapper.mapToEntity(order);
        orderJpaRepository.updateRealizedAndCompletedDateTime(
            orderToRealize.getOrderNumber(),
            orderToRealize.getRealized(),
            order.getCompletedDateTime()
        );
    }

    @Override
    public Page<Order> getPaginatedRealizedOrders(List<Integer> restaurantIds, boolean realized, Pageable pageable) {
        return orderJpaRepository.findByRestaurantIds(restaurantIds,realized,pageable)
            .map(orderEntityMapper::mapFromEntity);
    }

    @Override
    public void cancelOrder(Order order) {
        orderJpaRepository.deleteByOrderNumber(order.getOrderNumber());
    }
}
