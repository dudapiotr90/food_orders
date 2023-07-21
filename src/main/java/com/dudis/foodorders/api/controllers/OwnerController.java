package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.LocalDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.infrastructure.security.AuthorityException;
import com.dudis.foodorders.services.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@AllArgsConstructor
public class OwnerController {

    public static final String OWNER = "/owner";
    public static final String OWNER_ID = "/owner/{id}";
    public static final String OWNER_ADD = "/owner/{id}/add";


    private final OwnerService ownerService;

    @GetMapping(value = OWNER)
    public String getOwnerPage(HttpServletRequest request) {
        Integer loggedAccount = getLoggedInAccountId(request);
        return "redirect:/owner/" + loggedAccount;
    }

    @GetMapping(value = OWNER_ID)
    public ModelAndView getSpecificOwnerPage(@PathVariable Integer accountId, HttpServletRequest request) {
        if (!accountId.equals(getLoggedInAccountId(request))) {
            throw new AuthorityException("invalid PathVariable. You can access only your account!");
        }
        Map<String, ?> model = prepareOwnerData(accountId);

        return new ModelAndView("owner", model);
    }

    @GetMapping(value = OWNER_ADD)
    public String localFormPage(@PathVariable Integer accountId, ModelMap model) {
        model.addAttribute("local", LocalDTO.builder().build());
        model.addAttribute("ownerId", accountId);
        return "local_form";
    }

    @PostMapping(value = OWNER_ADD)
    public String addLocal(@PathVariable Integer accountId, ModelMap model) {
        // TODO addLocal

        return "redirect:/" + OWNER_ID; // TODO check if added local shows in table
    }

    private Integer getLoggedInAccountId(HttpServletRequest request) {
        String login = request.getRemoteUser();
        Account ownerAccount = ownerService.findOwnerByLogin(login);
        return ownerAccount.getAccountId();
    }

    private Map<String, ?> prepareOwnerData(Integer accountId) {
        var addedLocals = ownerService.findAllOwnerLocals(accountId);
        var pendingDeliveries = ownerService.findPendingDeliveries(accountId);
        var pendingBills = ownerService.findPendingBills(accountId);
        var owner = ownerService.findOwnerById(accountId);

        return Map.of(
            "locals", addedLocals,
            "deliveries", pendingDeliveries,
            "bills", pendingBills,
            "owner", owner
        );
    }
}
