package pl.dudis.foodorders.api.dtos;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
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
public class RegistrationRequestDTO {

    private String userName;
    private String userSurname;

    private String userLogin;
    private String userPassword;
    private String userConfirmPassword;
    @Email
    private String userEmail;
    @Size(min = 9, max = 12)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String userPhone;
    @Enumerated
    private ApiRoleDTO apiRole;

    private String userAddressCity;
    private String userAddressPostalCode;
    private String userAddressStreet;


}
