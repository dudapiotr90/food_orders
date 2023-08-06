package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.dtos.FoodRequestDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.OrderService;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MenuController {
    public static final String CUSTOMER_SHOW_MENU = "/customer/showMenu/{restaurantId}";
    public static final String CUSTOMER_SHOW_PAGINATED_MENU = "/customer/showMenu/{restaurantId}/page/{menuPageNumber}";

    private final SecurityUtils securityUtils;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;


    @GetMapping(value = CUSTOMER_SHOW_MENU)
    public String showMenu(@PathVariable(value = "restaurantId") Integer restaurantId, ModelMap model, HttpServletRequest request) {
        return paginatedMenu(restaurantId, 1, "foodId", "asc", model, request);
    }

    @GetMapping(value = CUSTOMER_SHOW_PAGINATED_MENU)
    public String paginatedMenu(
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @PathVariable(value = "menuPageNumber", required = false) int menuPageNumber,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("sortHow") String sortHow,
        ModelMap modelMap,
        HttpServletRequest request
    ) {
        Account loggedAccount = securityUtils.getLoggedInAccountId(request);
        CustomerDTO customer = customerService.findCustomerByAccountId(loggedAccount.getAccountId());
        int defaultPageSize = 10;
        RestaurantDTO restaurantDTO = restaurantService.findProcessingRestaurant(restaurantId);
        Page<FoodDTO> menuPage = restaurantService.getPaginatedMenu(menuPageNumber, defaultPageSize, sortBy, sortHow, restaurantId);
        modelMap.addAttribute("customer", customer);
        modelMap.addAttribute("restaurant", restaurantDTO);
        modelMap.addAttribute("foods", menuPage.getContent());
        modelMap.addAttribute("menuPage", menuPageNumber);
        modelMap.addAttribute("foodToAdd",new FoodRequestDTO());
        prepareMenuPaginatedAttributes(modelMap, menuPage, menuPageNumber, sortBy, sortHow);
        return "menu";
    }


    private void prepareMenuPaginatedAttributes(
        ModelMap modelMap,
        Page<FoodDTO> menuPage,
        int menuPageNumber,
        String sortBy,
        String sortHow
    ) {
        modelMap.addAttribute("reverseSortHow", sortHow.equals("asc") ? "desc" : "asc");
        modelMap.addAttribute("sortHow", sortHow);
        modelMap.addAttribute("sortBy", sortBy);
        modelMap.addAttribute("currentPage", menuPageNumber);
        modelMap.addAttribute("totalMenuPages", menuPage.getTotalPages());
        modelMap.addAttribute("totalMenuSize", menuPage.getTotalElements());
    }


}
