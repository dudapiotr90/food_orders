package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.BillService;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@AllArgsConstructor
public class CustomerController {

    public static final String CUSTOMER = "/customer";
    public static final String CUSTOMER_PAGE = "/customer/{id}";
    public static final String CANCEL_ORDER = "/customer/{id}/cancel/{orderNumber}";
    public static final String PAY = "/customer/{id}/pay";
    public static final String ORDER_HISTORY = "/customer/{id}/orderHistory";
    public static final String ORDER_HISTORY_PAGINATED = "/customer/{id}/orderHistory/page/{pageNumber}";

    private final SecurityUtils securityUtils;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final BillService billService;

    @GetMapping(value = CUSTOMER)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String getCustomerPage(HttpServletRequest request) {
        Account loggedAccount = securityUtils.getLoggedInAccountId(request);
        CustomerDTO customer = customerService.findCustomerByAccountId(loggedAccount.getAccountId());
        return customerPortal(customer.getCustomerId());
    }

    @GetMapping(value = CUSTOMER_PAGE)
    public ModelAndView getSpecificCustomerPage(@PathVariable(value = "id") Integer customerId, HttpServletRequest request) {
        securityUtils.checkAccess(customerId, request);
        Map<String, ?> model = prepareCustomerData(customerId, request);
        return new ModelAndView("customer", model);
    }

    @PutMapping(CANCEL_ORDER)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
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
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String payForOrder(
        @PathVariable(value = "id") Integer customerId,
        @RequestParam(value = "billNumber") String billNumber,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        customerService.payForBill(billNumber);

        return customerPortal(customerId);
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
        @PathVariable(value = "pageNumber") int pageNumber,
        @RequestParam("sortHow") String sortHow,
        @RequestParam("sortBy") String... sortBy
    ) {
        securityUtils.checkAccess(customerId, request);
        int defaultPageSize = 2;
        Page<OrderDTO> orders = customerService.findCustomerRealizedOrders(customerId, pageNumber, defaultPageSize, sortHow, sortBy);
        modelMap.addAttribute("customerId", customerId);
        prepareOrdersPaginatedAttributes(modelMap, orders, pageNumber, sortHow, sortBy);
        return "customer_order_history";
    }

    private Map<String, ?> prepareCustomerData(Integer customerId, HttpServletRequest request) {
        var customer = customerService.findCustomerById(customerId);
        var pendingBills = billService.findCustomerPayedBills(customerId, false);
        var restaurants = customerService.findRestaurantWithCustomerAddress(customer.getAccountId());
        var address = securityUtils.getLoggedInAccountId(request).getAddress();
        var orders = customerService.findCancelableOrders(customerId);

        return Map.of(
            "customer", customer,
            "pendingBills", pendingBills,
            "restaurants", restaurants,
            "address", address,
            "orders", orders
        );
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

    private String cartPortal(Integer customerId) {
        return String.format("redirect:/customer/%s/showCart", customerId);
    }

    private String customerPortal(Integer customerId) {
        return String.format("redirect:/customer/%s", customerId);
    }
}
