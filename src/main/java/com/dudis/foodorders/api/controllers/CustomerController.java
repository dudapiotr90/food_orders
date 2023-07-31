package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@AllArgsConstructor
public class CustomerController {

    public static final String CUSTOMER = "/customer";
    public static final String CUSTOMER_ID = "/customer/{id}";
    public static final String CUSTOMER_ADD_TO_CART = "/customer/{id}/add";

    public static final String CUSTOMER_SHOW_MENU = "/customer/showMenu/{restaurantId}";
    public static final String CUSTOMER_SHOW_PAGINATED_MENU = "/customer/showMenu/{restaurantId}/page/{menuPageNumber}";

    private final SecurityUtils securityUtils;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;

    @GetMapping(value = CUSTOMER)
    public String getCustomerPage(HttpServletRequest request) {
        Account loggedAccount = securityUtils.getLoggedInAccountId(request);
        CustomerDTO customer = customerService.findCustomerByAccountId(loggedAccount.getAccountId());
        return "redirect:/customer/" + customer.getCustomerId();
    }

    @GetMapping(value = CUSTOMER_ID)
    public ModelAndView getSpecificCustomerPage(@PathVariable(value = "id") Integer customerId, HttpServletRequest request) {
        securityUtils.checkAccess(customerId, request);
        Map<String, ?> model = prepareCustomerData(customerId, request);
        return new ModelAndView("customer", model);
    }

    @GetMapping(value = CUSTOMER_SHOW_MENU)
    public String showMenu(@PathVariable(value = "restaurantId") Integer restaurantId, ModelMap model,HttpServletRequest request) {
        return paginatedMenu(restaurantId, 1, "foodId", "asc", model,request);
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
        modelMap.addAttribute("buy", new OrderItemDTO());
        preparePaginatedAttributes(modelMap, menuPage, menuPageNumber, sortBy, sortHow);
        return "menu";
    }

    private Map<String, ?> prepareCustomerData(Integer customerId, HttpServletRequest request) {
        var customer = customerService.findCustomerById(customerId);
        var pendingBills = customerService.findPendingBills(customerId);
        var restaurants = customerService.findRestaurantWithCustomerAddress(customer.getAccountId());
        var address = securityUtils.getLoggedInAccountId(request).getAddress();

        return Map.of(
            "customer", customer,
            "pendingBills", pendingBills,
            "restaurants", restaurants,
            "address", address
        );
    }

    private void preparePaginatedAttributes(
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