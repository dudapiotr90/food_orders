package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.OwnerDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;
    private final AccountService accountService;

    public ConfirmationToken registerOwner(RegistrationRequest request) {
        Account ownerAccount = accountService.buildAccount(request);
        Owner owner = buildOwner(ownerAccount, request);
        return ownerDAO.registerOwner(owner);
    }

    private Owner buildOwner(Account ownerAccount, RegistrationRequest request) {
        return Owner.builder()
            .name(request.getUserName())
            .surname(request.getUserSurname())
            .account(ownerAccount)
            .build();
    }

    public Account findOwnerByLogin(String login) {
        return accountService.findByLogin(login);
    }
}
