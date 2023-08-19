package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.services.AccountService;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(DeveloperEndpoint.DEV)
public class StatisticsRestController {
    public static final String USER_ACCOUNTS = "/allUsers";
    public static final String CUSTOMERS = "/customers";
    public static final String OWNERS = "/owners";
    public static final String CUSTOMER = "/customer/{id}";
    public static final String OWNER = "/owner/{id}";

    private final AccountService accountService;
    private final CustomerService customerService;
    private final OwnerService ownerService;

    @Operation(summary = "Get users count")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Users found"
        )
    })
    @GetMapping(USER_ACCOUNTS)
    public long findHowManyUsersUseApplication() {
        return accountService.countAllAccounts();
    }


    @Operation(summary = "Get Sorted list of customers")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Customers found",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = CustomerDTO.class
                    )
                )
            }
        ),
    })
    @GetMapping(CUSTOMERS)
    public ResponseEntity<Page<CustomerDTO>> findAllCustomers(
        @Parameter(description = "Sort by customerDTO field", example = "name")
        @RequestParam(value = "sortBy") String sortBy,
        @Parameter(
            description = "Sort order for results (asc or desc)",
            schema = @Schema(allowableValues = {"asc", "desc"}),
            example = "asc"
        )
        @Valid @RequestParam(value = "sortHow") String sortHow,
        @Parameter(description = "Results per page, 5 as default", example = "5")
        @RequestParam(value = "pageSize", required = false) Integer pageSize,
        @Parameter(description = "By default returns 1st page")
        @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        return ResponseEntity.ok(customerService.findAllCustomers(sortBy, sortHow, pageSize, pageNumber));
    }

    @Operation(summary = "Get Customer by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Customer found",
            content = @Content(mediaType = "application/json"
            )),
        @ApiResponse(responseCode = "404",
            description = "Customer doesn't exist",
            content = @Content(mediaType = "application/json"
            ))
    })
    @GetMapping(CUSTOMER)
    public ResponseEntity<CustomerDTO> findCustomer(
        @Parameter(description = "Customer id", example = "3")
        @PathVariable(value = "id") Integer id
    ) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }


    @Operation(summary = "Get Sorted list of owners")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Owners found",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        implementation = OwnerDTO.class
                    )
                )
            }
        ),
    })
    @GetMapping(OWNERS)
    public ResponseEntity<Page<OwnerDTO>> findAllOwners(
        @Parameter(description = "Sort by ownerDTO field", example = "name")
        @RequestParam(value = "sortBy") String sortBy,
        @Parameter(
            description = "Sort order for results (asc or desc)",
            schema = @Schema(allowableValues = {"asc", "desc"}),
            example = "asc"
        )
        @Valid @RequestParam(value = "sortHow") String sortHow,
        @Parameter(description = "Results per page, 5 as default", example = "5")
        @RequestParam(value = "pageSize", required = false) Integer pageSize,
        @Parameter(description = "By default returns 1st page")
        @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        return ResponseEntity.ok(ownerService.findAllOwners(sortBy, sortHow, pageSize, pageNumber));
    }


    @Operation(summary = "Get Owner by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Owner found",
            content = {@Content(mediaType = "application/json")}
        ),
        @ApiResponse(responseCode = "404",
            description = "Owner doesn't exist",
            content = {@Content(mediaType = "application/json")}
        ),
    })
    @GetMapping(OWNER)
    public ResponseEntity<OwnerDTO> findOwner(
        @Parameter(description = "Owner id", example = "1")
        @PathVariable(value = "id") Integer id
    ) {
        return ResponseEntity.ok(ownerService.findOwnerById(id));
    }

}