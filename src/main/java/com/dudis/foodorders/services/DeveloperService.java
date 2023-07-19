package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.DeveloperDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;

@Service
@AllArgsConstructor
public class DeveloperService {

    private final DeveloperDAO developerDAO;
    private final AccountService accountService;

    public ConfirmationToken registerDeveloper(RegistrationRequest request) {
        Account developerAccount = accountService.buildAccount(request);
        Developer developer = buildDeveloper(developerAccount, request);
        return developerDAO.registerDeveloper(developer);
    }

    private Developer buildDeveloper(Account developerAccount, RegistrationRequest request) {
        return Developer.builder()
            .name(request.getUserName())
            .surname(request.getUserSurname())
            .account(developerAccount)
            .build();
    }
}
