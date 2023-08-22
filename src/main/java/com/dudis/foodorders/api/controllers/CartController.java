package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.CartService;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class CartController {
    public static final String SHOW_CART = "/customer/{id}/showCart";
    public static final String UPDATE_CART = "/customer/{id}/updateCart";
    public static final String DELETE_FROM_CART = "/customer/{id}/updateCart/delete/{orderItemId}";
    public static final String ORDER = "/customer/{id}/makeAnOrder";
    public static final String CUSTOMER_ADD_TO_CART = "/customer/{id}/{restaurantId}/add";

    private final SecurityUtils securityUtils;
    private final CartService cartService;
    private final RestaurantService restaurantService;
    private final CustomerService customerService;



    @PutMapping(UPDATE_CART)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String updateCartPosition(
        @PathVariable(value = "id") Integer customerId,
        @ModelAttribute("foodToUpdate") OrderItemDTO orderItem,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        cartService.updateOrderItem(orderItem);
        return showCart(customerId);
    }


    @DeleteMapping(DELETE_FROM_CART)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String deleteFromCart(
        @PathVariable(value = "id") Integer customerId,
        @PathVariable(value = "orderItemId") Integer orderItemId,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        cartService.deleteOrderItem(orderItemId);
        return showCart(customerId);
    }

    @PostMapping(ORDER)
    public String makeAnOrder(
        @PathVariable(value = "id") Integer customerId,
        @ModelAttribute(value = "foodsToOrder") OrderRequestDTO orderRequestDTO,
        ModelMap modelMap,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        RestaurantDTO restaurant = restaurantService.findProcessingRestaurant(orderRequestDTO.getRestaurantId());
        CustomerDTO customer = customerService.findCustomerById(customerId);
        String orderNumber = cartService.issueOrder(orderRequestDTO, restaurant, customer);
        modelMap.addAttribute("customer", customer);
        modelMap.addAttribute("orderNumber", orderNumber);
        return "order_issued";
    }


    @GetMapping(value = SHOW_CART)
    public String showCart(
        @PathVariable(value = "id") Integer customerId,
        ModelMap modelMap,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        List<OrderDetailsDTO> addedFoodItems = customerService.getAddedFoodItems(customerId);
        modelMap.addAttribute("orderRequests", addedFoodItems);
        modelMap.addAttribute("customerId", customerId);
        modelMap.addAttribute("foodToUpdate",new OrderItemDTO());
        modelMap.addAttribute("foodsToOrder",OrderRequestDTO.builder().build());
        return "cart";
    }

    @PostMapping(value = CUSTOMER_ADD_TO_CART)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
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

    private String showCart(Integer customerId) {
        return String.format("redirect:/customer/%s/showCart",customerId);
    }
    private String menuPortal(Integer restaurantId) {
        return String.format("redirect:/customer/showMenu/%s", restaurantId);
    }

}
