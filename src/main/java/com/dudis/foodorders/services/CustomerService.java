package com.dudis.foodorders.services;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.services.dao.CustomerDAO;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final AccountService accountService;

//    @Transactional
    public ConfirmationToken registerCustomer(RegistrationRequest request) {
        Account customerAccount = accountService.buildAccount(request);
        Customer customer = buildCustomer(customerAccount, request);
        return customerDAO.registerCustomer(customer);
    }

    private Customer buildCustomer(Account customerAccount, RegistrationRequest request) {
        return Customer.builder()
            .name(request.getUserName())
            .surname(request.getUserSurname())
            .account(customerAccount)
            .build();
    }
}
