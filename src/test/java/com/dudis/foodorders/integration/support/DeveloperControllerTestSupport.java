package com.dudis.foodorders.integration.support;

import com.dudis.foodorders.api.controllers.rest.DeveloperEndpoint;
import com.dudis.foodorders.api.controllers.rest.DeveloperRestController;
import com.dudis.foodorders.api.controllers.rest.RegistrationRestController;
import com.dudis.foodorders.api.controllers.rest.StatisticsRestController;
import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.dudis.foodorders.utils.AccountUtils.*;

public interface DeveloperControllerTestSupport {

    RequestSpecification requestSpecification();

    default String deleteInactiveAccounts() {
        return requestSpecification()
            .delete(DeveloperEndpoint.DEV + DeveloperRestController.REMOVE)
            .then()
            .statusCode(HttpStatus.OK.value())
            .and()
            .extract()
            .body().asString();
    }

    default long findAllAccounts() {
        return requestSpecification()
            .get(DeveloperEndpoint.DEV + StatisticsRestController.USER_ACCOUNTS)
            .then()
            .statusCode(HttpStatus.OK.value())
            .and()
            .extract()
            .as(Long.class);

    }

    default List<String> registerAccountsForDeletion() {
        List<RegistrationRequestDTO> accountToDelete = List.of(customerToDelete(), developerToDelete(), ownerToDelete());
       return accountToDelete.stream()
            .map(this::registerAccount)
            .toList();
    }


    default String registerAccount(RegistrationRequestDTO r) {
        return requestSpecification()
            .body(r)
            .post(DeveloperEndpoint.DEV + RegistrationRestController.REGISTER)
            .then()
            .statusCode(HttpStatus.OK.value())
            .and()
            .extract()
            .asString();
    }

    default UpdateAccountDTO updateAccount(UpdateAccountDTO updateRequest){
        return requestSpecification()
            .body(updateRequest)
            .put(DeveloperEndpoint.DEV + RegistrationRestController.UPDATE)
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(UpdateAccountDTO.class);
    }

    default ValidatableResponse updateAccountWithBadEmail(UpdateAccountDTO updateRequest){
        return requestSpecification()
            .body(updateRequest)
            .put(DeveloperEndpoint.DEV + RegistrationRestController.UPDATE)
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value());

    }

}
