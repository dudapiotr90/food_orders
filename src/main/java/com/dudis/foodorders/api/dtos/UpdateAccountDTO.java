package com.dudis.foodorders.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newUserLogin;

    @NotBlank(message = "Email is required")
    @Email
    private String userEmail;
    private String newEmail;
    @Size(min = 9, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newUserPhone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newUserAddressCity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newUserAddressPostalCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newUserAddressStreet;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String newUserResidenceNumber;

}
