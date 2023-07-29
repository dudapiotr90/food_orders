package com.dudis.foodorders.domain;

import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Set;

@With
@Value
@Builder
public class Customer {
    Integer customerId;
    String name;
    String surname;
    Account account;
    Cart cart;
    Set<Bill> bills;
}
