package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.LocalType;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import com.dudis.foodorders.infrastructure.database.mappers.CustomerEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderItemEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.RestaurantEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OrderJpaRepository;
import com.dudis.foodorders.services.dao.OrderDAO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRepository implements OrderDAO {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final OrderItemEntityMapper orderItemEntityMapper;

    @Autowired
    private final Clock clock;

//    @Override
//    public List<Order> findCancelableOrders(Integer customerId) {
//        return orderJpaRepository.findCancelableOrders(customerId).stream()
//            .filter(orderEntity -> orderEntity.getCancelTill().isAfter(OffsetDateTime.now()))
//            .map(orderEntityMapper::mapFromEntity)
//            .toList();
//    }
    @Override
    public List<Order> findCancelableOrders(Integer customerId) {
        return orderJpaRepository.findCancelableOrders(customerId).stream()
            .filter(orderEntity -> orderEntity.getCancelTill().isAfter(OffsetDateTime.now(clock)))
            .map(orderEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public List<Order> getRestaurantOrders(Integer restaurantId, boolean isInProgress) {
        return orderJpaRepository.findRestaurantOrdersInProgress(restaurantId,isInProgress).stream()
            .map(orderEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public Integer findPendingOrdersForRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.mapToEntity(restaurant);
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
    public Restaurant findRestaurantByOrderNumber(String orderNumber) {
        return orderJpaRepository.findRestaurantByOrderNumber(orderNumber).stream()
            .map(r -> Restaurant.builder()
                .restaurantId(Integer.valueOf((String) r[0]))
                .name((String) r[1])
                .type(LocalType.valueOf((String) r[3]))
                .build())
            .findFirst().orElse(null);

    }

    @Override
    public Page<Order> getPaginatedRealizedOwnerOrders(List<Integer> restaurantIds, boolean realized, Pageable pageable) {
        return orderJpaRepository.findByRestaurantIds(restaurantIds,realized,pageable)
            .map(orderEntityMapper::mapFromEntity);
    }

    @Override
    public Page<Order> getPaginatedRealizedCustomerOrders(Integer customerId, boolean realized, Pageable pageable) {
        return orderJpaRepository.findByCustomerIdAndRealized(customerId,realized,pageable)
            .map(o->Order.builder()
                .completedDateTime(o.getCompletedDateTime())
                .orderNumber(o.getOrderNumber())
                .receivedDateTime(o.getReceivedDateTime())
                .orderItems(orderItemEntityMapper.mapOrderItemsFromEntity(o.getOrderItems()))
                .restaurant(Restaurant.builder()
                    .name(o.getRestaurant().getName())
                    .type(o.getRestaurant().getType())
                    .build())
                .build());
    }

    @Override
    public void cancelOrder(Order order) {
        orderJpaRepository.deleteByOrderNumber(order.getOrderNumber());
    }
}
