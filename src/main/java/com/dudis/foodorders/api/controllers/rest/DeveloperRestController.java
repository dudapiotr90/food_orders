package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(DeveloperRestController.DEV)
public class DeveloperRestController {

    public static final int DEFAULT_PAGE_SIZE = 2;
    public static final String DEV = "/developer";

    public static final String USER_ACCOUNTS = "/allUsers";

    private final AccountService accountService;

    @GetMapping(USER_ACCOUNTS)
    public long findHowManyUsersUseApplication() {
        return accountService.countAllAccounts();
    }
}
