package com.dudis.foodorders.infrastructure.security;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.services.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityUtils {

    private final OwnerService ownerService;

    public void checkAccess(Integer ownerId, HttpServletRequest request) {
        OwnerDTO ownerToDisplay = ownerService.findOwnerById(ownerId);
        Account loggedInAccount = getLoggedInAccountId(request);
        if (!ownerToDisplay.getAccountId().equals(loggedInAccount.getAccountId())) {
            OwnerDTO ownerLogged = ownerService.findOwnerByAccountId(loggedInAccount.getAccountId());
            throw new AuthorityException(String.format("invalid PathVariable. You can access only your account! [/owner/[%s]/**]", ownerLogged.getOwnerId()));
        }
    }

    public Account getLoggedInAccountId(HttpServletRequest request) {
        String login = request.getRemoteUser();
        Account account = ownerService.findOwnerByLogin(login);
        return account;
    }
}
