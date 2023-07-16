package pl.dudis.foodorders.infrastructure.security;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.dudis.foodorders.domain.Account;
import pl.dudis.foodorders.domain.exception.RegistrationException;
import pl.dudis.foodorders.services.AccountService;
import pl.dudis.foodorders.services.CustomerService;
import pl.dudis.foodorders.services.DeveloperService;
import pl.dudis.foodorders.services.OwnerService;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final DeveloperService developerService;
    private final AccountService accountService;


    @Transactional
    public String registerAccount(RegistrationRequest request) {
        accountService.findByEmail(request.getUserEmail());

        return switch (Role.valueOf(request.getRole())) {
            case OWNER -> ownerService.registerOwner(request);
            case CUSTOMER -> customerService.registerCustomer(request);
            case DEVELOPER -> developerService.registerDeveloper(request);
            case ADMIN -> throw new RegistrationException("Cant create admin account. Only Admins have this permission");
        };
    }
}

