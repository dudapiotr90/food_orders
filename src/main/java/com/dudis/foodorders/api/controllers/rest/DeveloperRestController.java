package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.infrastructure.security.RegistrationService;
import com.dudis.foodorders.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dudis.foodorders.api.controllers.rest.DeveloperEndpoint.DEV;

@RestController
@AllArgsConstructor
@RequestMapping(DEV)
public class DeveloperRestController {

    public static final String REMOVE = "/remove";
    private final AccountService accountService;
    private final RegistrationService registrationService;


    @Operation(summary = "Remove not confirmed accounts")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Removed records",
            content = @Content(mediaType = "text/plain")
        )
    })
    @Transactional
    @DeleteMapping(REMOVE)
    public String deleteNotConfirmedAccounts() {
        List<Account> accountsToDelete =  accountService.findAccountsToDelete();
         accountsToDelete.forEach(registrationService::deleteAccount);
        registrationService.deleteTokens(accountsToDelete);

        accountService.deleteNotConfirmedAccounts();
        return "Records deleted: " + accountsToDelete.size();
    }
}
