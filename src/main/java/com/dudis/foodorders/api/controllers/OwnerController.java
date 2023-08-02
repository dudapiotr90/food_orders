package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.LocalType;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.OwnerService;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public static final String OWNER_SHOW = "/owner/{id}/show/{restaurantId}";
    private final SecurityUtils securityUtils;
    private final OwnerService ownerService;


    @GetMapping(value = OWNER)
    public String getOwnerPage(HttpServletRequest request) {
        Account loggedAccount = securityUtils.getLoggedInAccountId(request);
        OwnerDTO owner = ownerService.findOwnerByAccountId(loggedAccount.getAccountId());
        return "redirect:/owner/" + owner.getOwnerId();
    }

    @GetMapping(value = OWNER_ID)
    public ModelAndView getSpecificOwnerPage(@PathVariable(value = "id") Integer ownerId, HttpServletRequest request) {
        securityUtils.checkAccess(ownerId, request);
        Map<String, ?> model = prepareOwnerData(ownerId);
        return new ModelAndView("owner", model);
    }

    @GetMapping(value = OWNER_ADD)
    public String restaurantFormPage(@PathVariable(value = "id") Integer ownerId, ModelMap model,HttpServletRequest request) {
        securityUtils.checkAccess(ownerId, request);
        model.addAttribute("restaurant", RestaurantDTO.builder().build());
        model.addAttribute("ownerId", ownerId);
        model.addAttribute("types", LocalType.values());
        return "local_form";
    }

    @PostMapping(value = OWNER_ADD)
    public String addRestaurant(
        @PathVariable(value = "id") Integer ownerId,
        ModelMap model,
        @Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        ownerService.addRestaurant(ownerId, restaurantDTO);
        return "redirect:" + OWNER + "/" + ownerId;
    }





    private Map<String, ?> prepareOwnerData(Integer ownerId) {
        var addedRestaurants = ownerService.findAllOwnerRestaurants(ownerId);
        var pendingDeliveries = ownerService.findPendingDeliveries(ownerId);
        var pendingBills = ownerService.findOwnerPendingBills(ownerId);
        var owner = ownerService.findOwnerById(ownerId);

        return Map.of(
            "restaurants", addedRestaurants,
//            "deliveries", pendingDeliveries,
            "bills", pendingBills,
            "owner", owner
        );
    }
}
