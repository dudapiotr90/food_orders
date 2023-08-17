package com.dudis.foodorders.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {

    private String userName;
    private String userSurname;

    @Size(min = 4,max = 12)
    private String userLogin;
    private String userPassword;
    private String userConfirmPassword;
    @Email
    private String userEmail;
    @Size(min = 9, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String userPhone;
    private String role;

    private String userAddressCity;
    private String userAddressPostalCode;
    private String userAddressStreet;
    private String userResidenceNumber;


    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        Optional.ofNullable(userName).ifPresent(value -> result.put("userName", value));
        Optional.ofNullable(userSurname).ifPresent(value -> result.put("userSurname", value));
        Optional.ofNullable(userLogin).ifPresent(value -> result.put("userLogin", value));
        Optional.ofNullable(userPassword).ifPresent(value -> result.put("userPassword", value));
        Optional.ofNullable(userConfirmPassword).ifPresent(value -> result.put("userConfirmPassword", value));
        Optional.ofNullable(userEmail).ifPresent(value -> result.put("userEmail", value));
        Optional.ofNullable(userPhone).ifPresent(value -> result.put("userPhone", value));
        Optional.ofNullable(role).ifPresent(value -> result.put("role", value));
        Optional.ofNullable(userAddressCity).ifPresent(value -> result.put("userAddressCity", value));
        Optional.ofNullable(userAddressPostalCode).ifPresent(value -> result.put("userAddressPostalCode", value));
        Optional.ofNullable(userAddressStreet).ifPresent(value -> result.put("userAddressStreet", value));
        Optional.ofNullable(userResidenceNumber).ifPresent(value -> result.put("userResidenceNumber", value));
        return result;
    }
}
