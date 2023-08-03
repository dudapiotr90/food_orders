package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.List;

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
//    Set<OrderDetail> orderDetails;
    List<OrderItem> orderItems;
    Restaurant restaurant;
}
