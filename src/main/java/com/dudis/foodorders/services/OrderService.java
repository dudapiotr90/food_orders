package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.CustomerMapper;
import com.dudis.foodorders.api.mappers.OffsetDateTimeMapper;
import com.dudis.foodorders.api.mappers.OrderItemMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.domain.exception.OrderException;
import com.dudis.foodorders.services.dao.OrderDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderItemService orderItemService;
    private final RestaurantMapper restaurantMapper;
    private final PageableService pageableService;
    private final OrderItemMapper orderItemMapper;
    private final OffsetDateTimeMapper offsetDateTimeMapper;
    private final CustomerMapper customerMapper;

    public List<Order> findOrdersByInProgress(Integer restaurantId, boolean isInProgress) {
        return orderDAO.getRestaurantOrders(restaurantId, isInProgress);
    }

    public List<Order> findCancelableOrders(Integer customerId) {
        return orderDAO.findCancelableOrders(customerId);
    }

    public Integer countPendingOrdersForRestaurant(Restaurant restaurant) {
        return orderDAO.findPendingOrdersForRestaurant(restaurant);
    }

    public Order makeAnOrder(Set<OrderItem> orderItems, String customerComment, RestaurantDTO restaurantDTO, Customer customer) {
        Restaurant restaurant = restaurantMapper.mapFromDTO(restaurantDTO);
        Order order = buildOrderDetails(orderItems, restaurant, customerComment, customer);
        return orderDAO.issueAnOrder(order);
    }

    @Transactional
    public void cancelOrder(String orderNumber, Cart cart) {
        Order orderToCancel = orderDAO.findByOrderNumber(orderNumber)
            .orElseThrow(() -> new NotFoundException("Order with order number: [%s] doesn't exist".formatted(orderNumber)));
        if (orderToCancel.getCancelTill().isAfter(OffsetDateTime.now())) {
            orderItemService.returnOrderItemsToCartAndUncheckOrder(orderToCancel.getOrderItems(), cart);
            orderDAO.cancelOrder(orderToCancel);
        } else {
            throw new OrderException("You can't cancel order now. It was possible till: [%s]".formatted(orderToCancel.getCancelTill()));
        }
    }

    public Order findOrderByOrderNumber(String orderNumber) {
        return orderDAO.findByOrderNumber(orderNumber)
            .orElseThrow(() -> new NotFoundException("Order with order number: [%s] doesn't exist".formatted(orderNumber)));
    }

    public void setOrderAsInProgress(Order order) {
        orderDAO.setOrderAsInProgress(order);
    }

    public void realizeOrder(String orderNumber, Restaurant restaurant) {
        Optional<Order> order = orderDAO.findByOrderNumber(orderNumber);
        if (order.isEmpty()) {
            throw new NotFoundException("Order with order number: [%s] doesn't exist. Can't realize request".formatted(orderNumber));
        } else {
            Order orderToUpdate = order.get()
                .withCompletedDateTime(OffsetDateTime.now())
                .withRealized(true)
                .withRestaurant(restaurant);
            orderDAO.realizeOrder(orderToUpdate);
        }

    }

    public Page<OrderDTO> getPaginatedRealizedOwnerOrders(
        List<Integer> restaurantIds,
        Integer pageNumber,
        int pageSize,
        String sortHow,
        String... sortBy
    ) {
        Pageable pageable = pageableService.preparePageable(pageNumber, pageSize, sortHow, sortBy);
        Page<Order> paginatedRealizedOrders = orderDAO.getPaginatedRealizedOwnerOrders(restaurantIds, true, pageable);
        return new PageImpl<>(
            mapOrdersToOrdersDTO(paginatedRealizedOrders),
            pageable,
            paginatedRealizedOrders.getTotalElements()
        );

    }

    public Page<Order> getPaginatedRealizedCustomerOrders(
        Integer customerId,
        Integer pageNumber,
        int pageSize,
        String sortHow,
        String... sortBy
    ) {
        Pageable pageable = pageableService.preparePageable(pageNumber, pageSize, sortHow, sortBy);
        return orderDAO.getPaginatedRealizedCustomerOrders(customerId, true, pageable);
    }



    private Order buildOrderDetails(Set<OrderItem> orderItems, Restaurant restaurant, String customerComment, Customer customer) {
        return Order.builder()
            .orderNumber(UUID.randomUUID().toString())
            .receivedDateTime(OffsetDateTime.now())
            .cancelTill(OffsetDateTime.now().plusMinutes(20))
            .customerComment(customerComment)
            .realized(false)
            .inProgress(true)
            .orderItems(orderItems)
            .restaurant(restaurant)
            .customer(customer)
            .build();
    }

    private List<OrderDTO> mapOrdersToOrdersDTO(Page<Order> paginatedRealizedOrders) {
        return paginatedRealizedOrders.stream()
            .map(o -> OrderDTO.builder()
                .orderNumber(o.getOrderNumber())
                .orderItems(orderItemMapper.mapOrderItemsToDTO(o.getOrderItems()))
                .receivedDateTime(offsetDateTimeMapper.mapOffsetDateTimeToString(o.getReceivedDateTime()))
                .completedDateTime(offsetDateTimeMapper.mapOffsetDateTimeToString(o.getCompletedDateTime()))
                .customer(customerMapper.mapToDTO(o.getCustomer()))
                .restaurant(buildRestaurantDTO(o))
                .build())
            .toList();
    }

    private RestaurantDTO buildRestaurantDTO(Order o) {
        Restaurant restaurant = orderDAO.findRestaurantByOrderNumber(o.getOrderNumber());
        return RestaurantDTO.builder()
            .restaurantId(restaurant.getRestaurantId())
            .name(restaurant.getName())
            .type(restaurant.getType())
            .build();
    }
}
