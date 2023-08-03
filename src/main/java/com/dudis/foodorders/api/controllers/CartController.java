package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.dtos.OrderItemDTO;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class CartController {

    public static final String UPDATE_CART = "/customer/{id}/updateCart";

    private final SecurityUtils securityUtils;
    private final CartService cartService;


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

    private String showCart(Integer customerId) {
        return String.format("redirect:/customer/%s/showCart",customerId);
    }

}
