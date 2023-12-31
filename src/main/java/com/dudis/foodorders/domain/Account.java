package com.dudis.foodorders.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"accountId", "login", "email"})
public class Account {

    Integer accountId;
    String login;
    String password;
    String email;
    String phone;
    OffsetDateTime creationDate;
    Boolean status;
    Boolean unlocked;
    Boolean enabled;
    Integer roleId;
    Address address;
}
