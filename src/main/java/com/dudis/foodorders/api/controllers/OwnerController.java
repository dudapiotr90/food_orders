package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.services.OwnerService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class OwnerController {

    public static final String OWNER = "/owner";
    public static final String OWNER_ID = "/owner/{id}";

    private final OwnerService ownerService;

    @GetMapping(value = OWNER)
    public String getOwnerPage(HttpServletRequest request) {
        Integer loggedAccount = getLoggedInAccountId(request);
        return "redirect:/owner/" + loggedAccount;
    }

    @GetMapping(value = OWNER_ID)
    public String getSpecificOwnerPage(@PathVariable Integer accountId) {


        return "owner";
    }

    private Integer getLoggedInAccountId(HttpServletRequest request) {
        String login = request.getRemoteUser();
        Account ownerAccount = ownerService.findOwnerByLogin(login);
        return ownerAccount.getAccountId();
    }
}
