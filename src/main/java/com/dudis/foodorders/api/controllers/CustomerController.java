package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.OrderService;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CustomerController {

    public static final String CUSTOMER = "/customer";
    public static final String CUSTOMER_ID = "/customer/{id}";
    public static final String CUSTOMER_ADD_TO_CART = "/customer/{id}/{restaurantId}/add";

    public static final String CUSTOMER_SHOW_MENU = "/customer/showMenu/{restaurantId}";
    public static final String CUSTOMER_SHOW_CART = "/customer/{id}/showCart";
    public static final String CUSTOMER_SHOW_PAGINATED_MENU = "/customer/showMenu/{restaurantId}/page/{menuPageNumber}";

    public static final String CANCEL_ORDER = "/customer/{id}/cancel/{orderNumber}";
    public static final String PAY = "/customer/{id}/pay";

    public static final String ORDER_HISTORY = "/customer/{id}/orderHistory";
    public static final String ORDER_HISTORY_PAGINATED = "/customer/{id}/orderHistory/page/{pageNumber}";

    private final SecurityUtils securityUtils;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;

    @GetMapping(value = CUSTOMER)
    public String getCustomerPage(HttpServletRequest request) {
        Account loggedAccount = securityUtils.getLoggedInAccountId(request);
        CustomerDTO customer = customerService.findCustomerByAccountId(loggedAccount.getAccountId());
        return customerPortal(customer.getCustomerId());
    }

    @GetMapping(value = CUSTOMER_ID)
    public ModelAndView getSpecificCustomerPage(@PathVariable(value = "id") Integer customerId, HttpServletRequest request) {
        securityUtils.checkAccess(customerId, request);
        Map<String, ?> model = prepareCustomerData(customerId, request);
        return new ModelAndView("customer", model);
    }

    @GetMapping(value = CUSTOMER_SHOW_MENU)
    public String showMenu(@PathVariable(value = "restaurantId") Integer restaurantId, ModelMap model, HttpServletRequest request) {
        return paginatedMenu(restaurantId, 1, "foodId", "asc", model, request);
    }

    @PostMapping(value = CUSTOMER_ADD_TO_CART)
    public String addFoodToCart(
        @PathVariable(value = "id") Integer customerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("foodToAdd") FoodRequestDTO foodToAdd,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        customerService.addFoodToCart(customerId, foodToAdd);
        return menuPortal(restaurantId);
    }

    @GetMapping(value = CUSTOMER_SHOW_CART)
    public String showCart(
        @PathVariable(value = "id") Integer customerId,
        ModelMap modelMap,
        HttpServletRequest request
        ) {
        securityUtils.checkAccess(customerId, request);
        List<OrderDetailsDTO> restaurantsWithAddedFoodItems = customerService.getRestaurantsWithAddedFoodItems(customerId);
        modelMap.addAttribute("orderRequests", restaurantsWithAddedFoodItems);
        modelMap.addAttribute("customerId", customerId);
        modelMap.addAttribute("foodToUpdate",new OrderItemDTO());
        modelMap.addAttribute("foodsToOrder",OrderRequestDTO.builder().build());
        return "cart";
    }

    @PutMapping(CANCEL_ORDER)
    public String cancelOrder(
        @PathVariable(value = "id") Integer customerId,
        @PathVariable(value = "orderNumber") String orderNumber,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        Cart cart = customerService.findCartByCustomerId(customerId);
        orderService.cancelOrder(orderNumber, cart);

        return cartPortal(customerId);
    }

    @PutMapping(PAY)
    public String payForOrder(
        @PathVariable(value = "id") Integer customerId,
        @RequestParam(value = "billNumber") String billNumber,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        customerService.payForBill(billNumber);

        return customerPortal(customerId);
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


    @GetMapping(value = ORDER_HISTORY)
    public String checkOrderHistory(
        @PathVariable(value = "id") Integer customerId,
        ModelMap modelMap,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        return getPaginatedOrderHistory(customerId, modelMap, request, 1, "desc", "completedDateTime");
    }

    @GetMapping(ORDER_HISTORY_PAGINATED)
    public String getPaginatedOrderHistory(
        @PathVariable(value = "id") Integer customerId,
        ModelMap modelMap,
        HttpServletRequest request,
        @PathVariable(value = "pageNumber", required = false) int pageNumber,
        @RequestParam("sortHow") String sortHow,
        @RequestParam("sortBy") String... sortBy
    )
    {
        securityUtils.checkAccess(customerId, request);
        int defaultPageSize = 2;
        Page<OrderDTO> orders = customerService.findCustomerRealizedOrders(customerId, pageNumber, defaultPageSize, sortHow, sortBy);
        modelMap.addAttribute("customerId", customerId);
        prepareOrdersPaginatedAttributes(modelMap, orders, pageNumber, sortHow, sortBy);

        return "customer_order_history";
    }


    private Map<String, ?> prepareCustomerData(Integer customerId, HttpServletRequest request) {
        var customer = customerService.findCustomerById(customerId);
        var pendingBills = customerService.findPendingBills(customerId);
        var restaurants = customerService.findRestaurantWithCustomerAddress(customer.getAccountId());
        var address = securityUtils.getLoggedInAccountId(request).getAddress();
        var orders = customerService.findCancelableOrders(customerId);

        return Map.of(
            "customer", customer,
            "pendingBills", pendingBills,
            "restaurants", restaurants,
            "address", address,
            "orders",orders
        );
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

    private void prepareOrdersPaginatedAttributes(
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


    private String menuPortal(Integer restaurantId) {
        return String.format("redirect:/customer/showMenu/%s", restaurantId);
    }

    private String cartPortal(Integer customerId) {
        return String.format("redirect:/customer/%s/showCart", customerId);
    }
    private String customerPortal(Integer customerId) {
        return String.format("redirect:/customer/%s", customerId);
    }
}
