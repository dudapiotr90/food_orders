package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import com.dudis.foodorders.infrastructure.database.entities.RestaurantEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.Set;

@With
@Value
@Builder
public class Order {
    Integer orderId;
    String orderNumber;
    OffsetDateTime receivedDateTime;
    OffsetDateTime completedDateTime;
    String customerComment;
    Boolean realized;
    Set<OrderItem> orderItems;
    Restaurant restaurant;
    Customer customer;
}
