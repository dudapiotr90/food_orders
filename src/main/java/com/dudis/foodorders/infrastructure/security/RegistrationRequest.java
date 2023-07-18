package com.dudis.foodorders.infrastructure.security;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class RegistrationRequest {

    String userName;
    String userSurname;

    String userLogin;
    String userPassword;
    String userConfirmPassword;
    String userEmail;
    String userPhone;
    String role;

    String userAddressCity;
    String userAddressPostalCode;
    String userAddressStreet;
}
