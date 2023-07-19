package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class Developer {

    Integer developerId;
    String name;
    String surname;
    Account account;
}
