package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.api.dtos.OrderRequestDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.CartService;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class CartController {

    public static final String UPDATE_CART = "/customer/{id}/updateCart";
    public static final String DELETE_FROM_CART = "/customer/{id}/updateCart/delete/{orderItemId}";
    public static final String ORDER = "/customer/{id}/makeAnOrder";

    private final SecurityUtils securityUtils;
    private final CartService cartService;
    private final RestaurantService restaurantService;
    private final CustomerService customerService;



    @PutMapping(UPDATE_CART)
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
//        Cart cart = customerService.findCartByCustomerId(customerId);
        String orderNumber = cartService.issueOrder(orderRequestDTO, restaurant, customer);
        modelMap.addAttribute("customer", customer);
        modelMap.addAttribute("orderNumber", orderNumber);
        return "order_issued";
    }

    private String showCart(Integer customerId) {
        return String.format("redirect:/customer/%s/showCart",customerId);
    }

}
