package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.services.dao.AccountDAO;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountDAO accountDAO;

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


    public Account buildAccount(RegistrationRequestDTO request) {
        if (!request.getUserPassword().equals(request.getUserConfirmPassword())) {
            throw new ValidationException("Passwords input do not match");
        }
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
        Account accountUpdated = setNewDetails(updateRequest, accountToUpdate);
        Account accountAfterUpdate = accountDAO.updateAccount(accountUpdated);
        return UpdateAccountDTO.builder()
            .newUserLogin(accountAfterUpdate.getLogin())
            .userEmail(accountAfterUpdate.getEmail())
            .newUserPhone(accountAfterUpdate.getPhone())
            .newUserAddressCity(accountAfterUpdate.getAddress().getCity())
            .newUserAddressPostalCode(accountAfterUpdate.getAddress().getPostalCode())
            .newUserAddressStreet(accountAfterUpdate.getAddress().getStreet())
            .newUserResidenceNumber(accountAfterUpdate.getAddress().getResidenceNumber())
            .build();
    }

    private Account setNewDetails(UpdateAccountDTO updateRequest, Account accountToUpdate) {
        return accountToUpdate
            .withLogin(Objects.isNull(updateRequest.getNewUserLogin())
                ? accountToUpdate.getLogin() : updateRequest.getNewUserLogin())
            .withEmail(Objects.isNull(updateRequest.getNewEmail())
                ? accountToUpdate.getEmail() : updateRequest.getNewEmail())
            .withPhone(Objects.isNull(updateRequest.getNewUserPhone())
                ? accountToUpdate.getPhone() : updateRequest.getNewUserPhone())
            .withAddress(
                accountToUpdate.getAddress()
                    .withCity(Objects.isNull(updateRequest.getNewUserAddressCity())
                        ? accountToUpdate.getAddress().getCity() : updateRequest.getNewUserAddressCity())
                    .withPostalCode(Objects.isNull(updateRequest.getNewUserAddressPostalCode())
                        ? accountToUpdate.getAddress().getPostalCode() : updateRequest.getNewUserAddressPostalCode())
                    .withStreet(Objects.isNull(updateRequest.getNewUserAddressStreet())
                        ? accountToUpdate.getAddress().getStreet() : updateRequest.getNewUserAddressStreet())
                    .withResidenceNumber(Objects.isNull(updateRequest.getNewUserResidenceNumber())
                        ? accountToUpdate.getAddress().getResidenceNumber() : updateRequest.getNewUserResidenceNumber())
            );
    }


    public void deleteNotConfirmedAccounts() {
        accountDAO.deleteNotConfirmedAccounts();
    }

    public List<Account> findAccountsToDelete() {
        return accountDAO.findAllAccountByEnabled(false);
    }
}
