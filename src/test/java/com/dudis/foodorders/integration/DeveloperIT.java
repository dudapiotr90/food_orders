package com.dudis.foodorders.integration;


import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import com.dudis.foodorders.integration.configuration.RestAssuredIntegrationTestBase;
import com.dudis.foodorders.integration.support.DeveloperControllerTestSupport;
import com.dudis.foodorders.utils.AccountUtils;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeveloperIT
    extends RestAssuredIntegrationTestBase
    implements DeveloperControllerTestSupport {

    @Test
    void inactiveAccountsDeleteSuccessfully() {
        // Given
        logInto("devITtest", "developer");
        List<String> listOfTokens = registerAccountsForDeletion();
        long allAccounts = findAllAccounts();
        assertThat(allAccounts).isGreaterThan(3);

        // When
        String deletionMessage = deleteInactiveAccounts();

        // Then
        long allActiveAccounts = findAllAccounts();
        assertThat(allAccounts).isGreaterThan(allActiveAccounts);
        assertThat(deletionMessage).isEqualTo("Records deleted: %s".formatted(listOfTokens.size()));
    }

    @Test
    void updateAccountSuccessfully() {
        // Given
        logInto("devITtest","developer");
        RegistrationRequestDTO request = AccountUtils.developerToDelete();
        String accountToUpdate = registerAccount(request);
        assertThat(accountToUpdate).isNotEmpty();

        // When
        UpdateAccountDTO updated = updateAccount(AccountUtils.someUpdateRequest2().withUserEmail(request.getUserEmail()));

        // Then
        assertThat(updated.getNewUserAddressCity()).isNotEqualTo(request.getUserAddressCity());

    }
    @Test
    void updateAccountWithNonExistingEmail() {
        // Given
        logInto("devITtest","developer");
        RegistrationRequestDTO request = AccountUtils.developerToDelete();
        String accountToUpdate = registerAccount(request);
        assertThat(accountToUpdate).isNotEmpty();

        // When
        ValidatableResponse validatableResponse = updateAccountWithBadEmail(AccountUtils.someUpdateRequest2().withUserEmail("bad@email"));

        // Then
        assertThat(validatableResponse.extract().statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
