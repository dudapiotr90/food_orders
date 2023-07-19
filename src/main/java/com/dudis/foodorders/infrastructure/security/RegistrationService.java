package com.dudis.foodorders.infrastructure.security;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.exception.RegistrationException;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final DeveloperService developerService;
    private final AccountService accountService;

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    @Transactional
    public void registerAccount(RegistrationRequest request) {
        accountService.findByEmail(request.getUserEmail());

        ConfirmationToken confirmationToken = switch (Role.valueOf(request.getRole())) {
            case OWNER -> ownerService.registerOwner(request);
            case CUSTOMER -> customerService.registerCustomer(request);
            case DEVELOPER -> developerService.registerDeveloper(request);
            case ADMIN -> throw new RegistrationException("Cant create admin account. Only Admins have this permission");
        };
        String token = confirmationTokenService.saveConfirmationToken(confirmationToken);

        String confirmationEmail = emailSender.buildConfirmationEmail(token, request);
        emailSender.send(request.getUserEmail(), confirmationEmail);
    }

    @Transactional
    public Account confirmToken(String token) {
        return confirmationTokenService.confirmToken(token);
    }
}

