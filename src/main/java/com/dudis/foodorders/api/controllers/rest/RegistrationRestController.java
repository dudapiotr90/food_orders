package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import com.dudis.foodorders.infrastructure.security.RegistrationService;
import com.dudis.foodorders.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(DeveloperEndpoint.DEV)
public class RegistrationRestController {

    public static final String REGISTER = "/register";
    public static final String UPDATE = "/updateAccount";

    private final RegistrationService registrationService;
    private final AccountService accountService;


    @Operation(summary = "Register account")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Account registration completed. Confirm account",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Wrong input data",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "409",
            content = @Content(mediaType = "application/json")
        )
    })
    @PostMapping(REGISTER)
    public String registerAccount(
        @RequestBody RegistrationRequestDTO request
    ) {
        return registrationService.registerAccount(request);
    }

    @Operation(summary = "Update account")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Account successfully updated",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Non existing email",
            content = @Content(mediaType = "application/json")
        )
    })
    @PutMapping(UPDATE)
    public ResponseEntity<UpdateAccountDTO> updateAccount(
        @Valid @RequestBody UpdateAccountDTO updateRequest
    ) {
        UpdateAccountDTO updated  = accountService.updateAccount(updateRequest);
        return ResponseEntity.ok(updated);
    }

}
