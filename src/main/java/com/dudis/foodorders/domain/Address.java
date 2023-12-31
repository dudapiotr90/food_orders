package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class Address {
    Integer addressId;
    String city;
    String postalCode;
    String street;
    String residenceNumber;
    Account account;
}
