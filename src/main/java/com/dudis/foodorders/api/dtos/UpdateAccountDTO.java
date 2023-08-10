package com.dudis.foodorders.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userLogin;

    @NotBlank(message = "Email is required")
    @Email
    private String userEmail;
    private String newEmail;
    @Size(min = 9, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userPhone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userAddressCity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userAddressPostalCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userAddressStreet;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userResidenceNumber;

}
