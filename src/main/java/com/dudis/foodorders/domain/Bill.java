package com.dudis.foodorders.domain;

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
