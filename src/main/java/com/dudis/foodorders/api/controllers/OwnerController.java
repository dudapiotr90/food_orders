package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.LocalType;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.BillService;
import com.dudis.foodorders.services.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@AllArgsConstructor
public class OwnerController {

    public static final String OWNER = "/owner";
    public static final String OWNER_PAGE = "/owner/{id}";
    public static final String OWNER_ADD = "/owner/{id}/add";
    public static final String ORDER_HISTORY = "/owner/{id}/orderHistory";
    public static final String ORDER_HISTORY_PAGINATED = "/owner/{id}/orderHistory/page/{pageNumber}";
    private final SecurityUtils securityUtils;
    private final OwnerService ownerService;
    private final BillService billService;

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping(value = OWNER)
    public String getOwnerPage(HttpServletRequest request) {
        Account loggedAccount = securityUtils.getLoggedInAccountId(request);
        OwnerDTO owner = ownerService.findOwnerByAccountId(loggedAccount.getAccountId());
        return "redirect:/owner/" + owner.getOwnerId();
    }
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping(value = OWNER_PAGE)
    public ModelAndView getSpecificOwnerPage(@PathVariable(value = "id") Integer ownerId, HttpServletRequest request) {
        securityUtils.checkAccess(ownerId, request);
        Map<String, ?> model = prepareOwnerData(ownerId);
        return new ModelAndView("owner", model);
    }
    @GetMapping(value = OWNER_ADD)
    public String restaurantFormPage(@PathVariable(value = "id") Integer ownerId, ModelMap model, HttpServletRequest request) {
        securityUtils.checkAccess(ownerId, request);
        model.addAttribute("restaurant", RestaurantDTO.builder().build());
        model.addAttribute("ownerId", ownerId);
        model.addAttribute("types", LocalType.values());
        return "local_form";
    }

    @PostMapping(value = OWNER_ADD)
    public String addRestaurant(
        @PathVariable(value = "id") Integer ownerId,
        @Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        ownerService.addRestaurant(ownerId, restaurantDTO);
        return "redirect:" + OWNER + "/" + ownerId;
    }

    @GetMapping(value = ORDER_HISTORY)
    public String checkOrderHistory(
        @PathVariable(value = "id") Integer ownerId,
        ModelMap modelMap,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        return getPaginatedOrderHistory(ownerId, modelMap, request, 1, "desc", "receivedDateTime");
    }

    @GetMapping(ORDER_HISTORY_PAGINATED)
    public String getPaginatedOrderHistory(
        @PathVariable(value = "id") Integer ownerId,
        ModelMap modelMap,
        HttpServletRequest request,
        @PathVariable(value = "pageNumber", required = false) int pageNumber,
        @RequestParam("sortHow") String sortHow,
        @RequestParam("sortBy") String... sortBy
        )
    {
        securityUtils.checkAccess(ownerId, request);
        int defaultPageSize = 2;
        Page<OrderDTO> orders = ownerService.findOwnerRealizedOrders(ownerId, pageNumber, defaultPageSize, sortHow, sortBy);
        modelMap.addAttribute("ownerId", ownerId);
        preparePaginatedAttributes(modelMap, orders, pageNumber, sortHow, sortBy);

        return "owner_order_history";
    }

    private Map<String, ?> prepareOwnerData(Integer ownerId) {
        var addedRestaurants = ownerService.findAllOwnerRestaurants(ownerId);
        var pendingBills = billService.findOwnerPendingBills(ownerId, false);
        var owner = ownerService.findOwnerById(ownerId);

        return Map.of(
            "restaurants", addedRestaurants,
            "bills", pendingBills,
            "owner", owner
        );
    }

    private void preparePaginatedAttributes(
        ModelMap modelMap,
        Page<OrderDTO> orders,
        int pageNumber,
        String sortHow,
        String... sortBy
    ) {
        modelMap.addAttribute("orders", orders.getContent());
        modelMap.addAttribute("reverseSortHow", sortHow.equals("asc") ? "desc" : "asc");
        modelMap.addAttribute("sortHow", sortHow);
        modelMap.addAttribute("sortBy", sortBy);
        modelMap.addAttribute("currentPage", pageNumber);
        modelMap.addAttribute("totalPages", orders.getTotalPages());
        modelMap.addAttribute("totalSize", orders.getTotalElements());
    }
}
