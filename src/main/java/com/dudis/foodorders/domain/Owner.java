package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.Set;

@With
@Value
@Builder
public class Owner {

    Integer ownerId;
    String name;
    String surname;
    Account account;
    Set<Restaurant> restaurants;
    Set<Bill> bills;
    Set<Delivery> deliveries;
}
