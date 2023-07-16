package pl.dudis.foodorders.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dudis.foodorders.domain.Account;
import pl.dudis.foodorders.domain.Customer;
import pl.dudis.foodorders.infrastructure.security.RegistrationRequest;
import pl.dudis.foodorders.services.dao.CustomerDAO;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final AccountService accountService;

//    @Transactional
    public String registerCustomer(RegistrationRequest request) {
        Account customerAccount = accountService.buildAccount(request);
        Customer customer = buildCustomer(customerAccount, request);
        customerDAO.registerCustomer(customer);
        return "";
    }

    private Customer buildCustomer(Account customerAccount, RegistrationRequest request) {
        return Customer.builder()
            .name(request.getUserName())
            .surname(request.getUserSurname())
            .account(customerAccount)
            .build();
    }
}
