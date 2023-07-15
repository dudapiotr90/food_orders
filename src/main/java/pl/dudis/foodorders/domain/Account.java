package pl.dudis.foodorders.domain;

import lombok.*;
import pl.dudis.foodorders.infrastructure.security.ApiRole;

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
    Address address;
    String apiRole;
}
