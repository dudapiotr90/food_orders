package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;

@With
@Value
@Builder
public class Delivery {

    Integer deliveryId;
    String deliveryNumber;
    BigDecimal price;
    Boolean delivered;
    Restaurant restaurant;
    Owner owner;
}
