package com.dudis.foodorders.infrastructure.security;

import com.dudis.foodorders.api.controllers.RegistrationController;
import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.exception.RegistrationException;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.dudis.foodorders.infrastructure.security.Role.valueOf;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    public static final String REGISTRATION_LINK_FORM =
        "http://localhost:%s/food-orders" + RegistrationController.REGISTRATION_CONFIRM + "?token=";

    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final DeveloperService developerService;
    private final AccountService accountService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final ApiRoleService apiRoleService;
    @Value("${server.port}")
    private String port;

    @Transactional
    public String registerAccount(RegistrationRequestDTO request) {
        Account account = accountService.findByEmail(request.getUserEmail());
        if (Objects.nonNull(account)) {
            throw new RegistrationException(
                String.format("User with email: [%s] already exists", account.getEmail()));
        }
        ConfirmationToken confirmationToken = switch (Role.valueOf(request.getRole())) {
            case OWNER -> ownerService.registerOwner(request);
            case CUSTOMER -> customerService.registerCustomer(request);
            case DEVELOPER -> developerService.registerDeveloper(request);
            case ADMIN -> throw new RegistrationException("Cant create admin account. Only Admins have this permission");
        };
        String token = confirmationTokenService.saveConfirmationToken(confirmationToken);
        String confirmationLink = REGISTRATION_LINK_FORM.formatted(port) + token;
        String confirmationEmail = emailSender.buildConfirmationEmail(token, request,confirmationLink);
        emailSender.send(request.getUserEmail(), confirmationEmail);
        return confirmationLink;
    }

    @Transactional
    public Account confirmToken(String token) {
        return confirmationTokenService.confirmToken(token);
    }

    @Transactional
    public void deleteAccount(Account account) {
        String role = apiRoleService.findRoleById(account.getRoleId());
        switch (valueOf(role)) {
            case CUSTOMER -> customerService.deleteCustomer(account.getAccountId());
            case OWNER -> ownerService.deleteOwner(account.getAccountId());
            case DEVELOPER -> developerService.deleteDeveloper(account.getAccountId());
        }
    }

    @Transactional
    public void deleteTokens(List<Account> accountsToDelete) {
        List<Integer> ids = accountsToDelete.stream()
            .map(Account::getAccountId)
            .toList();
        confirmationTokenService.deleteTokensByAccount(ids);
    }
}

