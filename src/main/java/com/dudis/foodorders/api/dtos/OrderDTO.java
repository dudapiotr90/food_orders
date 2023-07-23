package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private String orderNumber;
    private String receivedDateTime;
    private String completedDateTime;
    private String customerComment;
    private Boolean realized;
    private Set<OrderItemDTO> orderItems;
}
