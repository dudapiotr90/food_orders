package com.dudis.foodorders.services;

import com.dudis.foodorders.infrastructure.security.ApiRoleService;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.services.dao.AccountDAO;

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

    public void enableAccount(Integer accountId) {
        accountDAO.enableAccount(accountId);
    }

    public Account findByLogin(String login) {
        Optional<Account> account = accountDAO.findByLogin(login);
        if (account.isEmpty()) {
            throw new NotFoundException(
                String.format("User with login: [%s] doesn't exists", login));
        }
        return account.get();
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
                .street(request.getUserAddressStreet())
                .residenceNumber(request.getUserResidenceNumber())
                .build())
            .roleId(accountDAO.findByRole(request.getRole()))
            .build();
    }

    public Account findCustomerAccount(Integer accountId) {
        return accountDAO.findById(accountId)
            .orElseThrow(() -> new NotFoundException("Account with id: [%s] doesn't exist".formatted(accountId)));
    }

}
