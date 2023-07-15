package pl.dudis.foodorders.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dudis.foodorders.domain.Account;
import pl.dudis.foodorders.domain.Address;
import pl.dudis.foodorders.domain.exception.NotFoundException;
import pl.dudis.foodorders.infrastructure.security.RegistrationRequest;
import pl.dudis.foodorders.services.dao.AccountDAO;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountDAO accountDAO;

    public void findByEmail(String userEmail) {
        Optional<Account> account = accountDAO.findByEmail(userEmail);
        if (account.isPresent()) {
            throw new NotFoundException(
                String.format("User with email: [%s] already exists", userEmail));
        }
    }

    public Account buildAccount(RegistrationRequest request) {
        return Account.builder()
            .login(request.getUserLogin())
            .password(request.getUserPassword())
            .email(request.getUserEmail())
            .phone(request.getUserPhone())
            .creationDate(OffsetDateTime.now())
            .address(Address.builder()
                .city(request.getUserAddressCity())
                .postalCode(request.getUserAddressPostalCode())
                .address(request.getUserAddressStreet())
                .build())
            .apiRole(request.getRole())
            .build();
    }
}
