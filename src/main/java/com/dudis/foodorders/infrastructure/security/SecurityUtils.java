package com.dudis.foodorders.infrastructure.security;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.services.AccountService;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.DeveloperService;
import com.dudis.foodorders.services.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SecurityUtils {

    private final OwnerService ownerService;
    private final CustomerService customerService;
    private final DeveloperService developerService;
    private final AccountService accountService;
    private final ApiRoleService apiRoleService;

    public void checkAccess(Integer id, HttpServletRequest request) {
        Account loggedInAccount = getLoggedInAccountId(request);
        Integer roleId = loggedInAccount.getRoleId();
        List<ApiRole> apiRoles = apiRoleService.findApiRoles();
        Map<Integer, String> roleMap = apiRoles.stream()
            .collect(Collectors.toMap(ApiRole::getApiRoleId, ApiRole::getRole));
        String role = roleMap.get(roleId);
        switch (Role.valueOf(role)) {
            case CUSTOMER -> checkCustomerAccess(id, loggedInAccount);
            case OWNER -> checkOwnerAccess(id, loggedInAccount);
            case DEVELOPER -> checkDeveloperAccess(loggedInAccount);
        }
    }

    private void checkDeveloperAccess(Account loggedInAccount) {
        developerService.findDeveloperByAccountId(loggedInAccount.getAccountId());

    }

    private void checkCustomerAccess(Integer id, Account loggedInAccount) {
        CustomerDTO customerToDisplay = customerService.findCustomerById(id);
        if (!customerToDisplay.getAccountId().equals(loggedInAccount.getAccountId())) {
            CustomerDTO customerLogged = customerService.findCustomerByAccountId(loggedInAccount.getAccountId());
            throw new AuthorityException(String.format("invalid PathVariable. You can access only your account! [/customer/[%s]/**]",
                customerLogged.getCustomerId()));
        }
    }

    private void checkOwnerAccess(Integer ownerId, Account loggedInAccount) {
        OwnerDTO ownerToDisplay = ownerService.findOwnerById(ownerId);
        if (!ownerToDisplay.getAccountId().equals(loggedInAccount.getAccountId())) {
            OwnerDTO ownerLogged = ownerService.findOwnerByAccountId(loggedInAccount.getAccountId());
            throw new AuthorityException(String.format("invalid PathVariable. You can access only your account! [/owner/[%s]/**]", ownerLogged.getOwnerId()));
        }
    }

    public Account getLoggedInAccountId(HttpServletRequest request) {
        String login = request.getRemoteUser();
        return accountService.findByLogin(login);
    }
}
