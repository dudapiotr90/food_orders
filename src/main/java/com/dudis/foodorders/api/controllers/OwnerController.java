package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.FoodTypeDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.LocalType;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.security.AuthorityException;
import com.dudis.foodorders.services.OwnerService;
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
import java.util.Objects;

@Controller
@AllArgsConstructor
public class OwnerController {

    public static final String OWNER = "/owner";
    public static final String OWNER_ID = "/owner/{id}";
    public static final String OWNER_ADD = "/owner/{id}/add";
    public static final String OWNER_SHOW = "/owner/{id}/show/{restaurantId}";
    // TODO SHOW przerobić na ilość zamówień, ilość pozycji menu



    private final OwnerService ownerService;
    private final RestaurantMapper restaurantMapper;

    @GetMapping(value = OWNER)
    public String getOwnerPage(HttpServletRequest request) {
        if (Objects.isNull(request.getRemoteUser())) {
            return "redirect:/login";
        }
        Integer loggedAccount = getLoggedInAccountId(request);
        OwnerDTO owner = ownerService.findOwnerByAccountId(loggedAccount);
        return "redirect:/owner/" + owner.getOwnerId();
    }

    @GetMapping(value = OWNER_ID)
    public ModelAndView getSpecificOwnerPage(@PathVariable(value = "id") Integer ownerId, HttpServletRequest request) {
        OwnerDTO owner = ownerService.findOwnerById(ownerId);
        Integer loggedInAccount = getLoggedInAccountId(request);
        if (!owner.getAccountId().equals(loggedInAccount)) {
            throw new AuthorityException(
                String.format("invalid PathVariable. You can access only your account!. /owner/%s",
                loggedInAccount));
        }
        Map<String, ?> model = prepareOwnerData(ownerId);

        return new ModelAndView("owner_portal", model);
    }

    @GetMapping(value = OWNER_ADD)
    public String restaurantFormPage(@PathVariable(value = "id") Integer ownerId, ModelMap model) {
        model.addAttribute("restaurant", RestaurantDTO.builder().build());
        model.addAttribute("ownerId", ownerId);
        model.addAttribute("types", LocalType.values());
        return "local_form";
    }

    @PostMapping(value = OWNER_ADD)
    public String addRestaurant(
        @PathVariable(value = "id") Integer ownerId,
        ModelMap model,
        @Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDTO
    ) {
        ownerService.addRestaurant(ownerId, restaurantDTO);


        return "redirect:" + OWNER + "/" + ownerId;
    }


    private Integer getLoggedInAccountId(HttpServletRequest request) {
        String login = request.getRemoteUser();
        Account ownerAccount = ownerService.findOwnerByLogin(login);
        return ownerAccount.getAccountId();
    }


    private Map<String, ?> prepareOwnerData(Integer ownerId) {
        var addedRestaurants = ownerService.findAllOwnerRestaurants(ownerId);
        var pendingDeliveries = ownerService.findPendingDeliveries(ownerId);
        var pendingBills = ownerService.findPendingBills(ownerId);
        var owner = ownerService.findOwnerById(ownerId);

        return Map.of(
            "restaurants", addedRestaurants,
            "deliveries", pendingDeliveries,
            "bills", pendingBills,
            "owner", owner
        );
    }
}
