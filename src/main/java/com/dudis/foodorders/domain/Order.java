package com.dudis.foodorders.domain;

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
    Boolean inProgress;
    OffsetDateTime cancelTill;
    Set<OrderItem> orderItems;
    Restaurant restaurant;
    Customer customer;
}
