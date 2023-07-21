package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.LocalEntity;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
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
    Local local;
    Owner owner;
}
