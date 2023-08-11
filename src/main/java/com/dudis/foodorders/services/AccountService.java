package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import com.dudis.foodorders.api.mappers.AccountMapper;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.security.ApiRoleService;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.services.dao.AccountDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountDAO accountDAO;
    private final ApiRoleService apiRoleService;
    private final AccountMapper accountMapper;

    public Account findByEmail(String userEmail) {
        Optional<Account> account = accountDAO.findByEmail(userEmail);
        return account.orElse(null);
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

    public long countAllAccounts() {
        return accountDAO.countAllAccounts();
    }

    @Transactional
    public UpdateAccountDTO updateAccount(UpdateAccountDTO updateRequest) {
        Account accountToUpdate = findByEmail(updateRequest.getUserEmail());
        if (Objects.isNull(accountToUpdate)) {
            throw new NotFoundException("Can't update non existing account");
        }
        Account accountUpdated = setNewDetails(updateRequest,accountToUpdate);
        Account account = accountDAO.updateAccount(accountUpdated);
        return UpdateAccountDTO.builder()
            .userLogin(account.getLogin())
            .userEmail(account.getEmail())
            .userPhone(account.getPhone())
            .userAddressCity(account.getAddress().getCity())
            .userAddressPostalCode(account.getAddress().getPostalCode())
            .userAddressStreet(account.getAddress().getStreet())
            .userResidenceNumber(account.getAddress().getResidenceNumber())
            .build();
    }

    private Account setNewDetails(UpdateAccountDTO updateRequest, Account accountToUpdate) {
        return accountToUpdate
            .withLogin(Objects.isNull(updateRequest.getUserLogin())
                ? accountToUpdate.getLogin() : updateRequest.getUserLogin())
            .withEmail(Objects.isNull(updateRequest.getNewEmail())
                ? accountToUpdate.getEmail() : updateRequest.getNewEmail())
            .withPhone(Objects.isNull(updateRequest.getUserPhone())
                ? accountToUpdate.getPhone() : updateRequest.getUserPhone())
            .withAddress(
                accountToUpdate.getAddress()
                .withCity(Objects.isNull(updateRequest.getUserAddressCity())
                    ? accountToUpdate.getAddress().getCity() : updateRequest.getUserAddressCity())
                .withPostalCode(Objects.isNull(updateRequest.getUserAddressPostalCode())
                    ? accountToUpdate.getAddress().getPostalCode() : updateRequest.getUserAddressPostalCode())
                .withStreet(Objects.isNull(updateRequest.getUserAddressStreet())
                    ? accountToUpdate.getAddress().getStreet() : updateRequest.getUserAddressStreet())
                .withResidenceNumber(Objects.isNull(updateRequest.getUserResidenceNumber())
                    ? accountToUpdate.getAddress().getResidenceNumber() : updateRequest.getUserResidenceNumber())
            );
    }
}
