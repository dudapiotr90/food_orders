package com.dudis.foodorders.infrastructure.security;

import com.dudis.foodorders.api.controllers.RegistrationController;
import com.dudis.foodorders.api.mappers.AccountMapper;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.exception.RegistrationException;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class RegistrationService {
    private static final String REGISTRATION_LINK_FORM = "http://localhost:8150/food-orders" + RegistrationController.REGISTRATION_CONFIRM + "?token=";
    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final DeveloperService developerService;
    private final AccountService accountService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    @Transactional
    public String registerAccount(RegistrationRequest request) {
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
        String confirmationLink = REGISTRATION_LINK_FORM + token;
        String confirmationEmail = emailSender.buildConfirmationEmail(token, request,confirmationLink);
        emailSender.send(request.getUserEmail(), confirmationEmail);
        return confirmationLink;
    }

    @Transactional
    public Account confirmToken(String token) {
        return confirmationTokenService.confirmToken(token);
    }


}

