package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@With
@Value
@Builder
public class Bill {

    Integer billId;
    String billNumber;
    OffsetDateTime dateTime;
    BigDecimal amount;
    Boolean payed;
    Owner owner;
    Customer customer;
    Order order;
}
