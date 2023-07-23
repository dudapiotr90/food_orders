package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.DeliveryAddressesDTO;
import com.dudis.foodorders.api.dtos.MenuDTO;
import com.dudis.foodorders.api.dtos.OrdersDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.services.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(name = "/owner/{id}")
@AllArgsConstructor
public class RestaurantController {

    public static final String MANAGE = "/manage/{restaurantId}";

    private final RestaurantService restaurantService;

    @GetMapping(value = MANAGE)
    public String manageRestaurant(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        ModelMap modelMap
    ) {
        RestaurantDTO restaurantDTO = restaurantService.findProcessingRestaurant(restaurantId);
        MenuDTO menuDTO = restaurantService.getCurrentMenu(restaurantId);
        DeliveryAddressesDTO deliveriesDTO = restaurantService.getDeliveryAddresses(restaurantId);
        OrdersDTO ordersDTO = restaurantService.findOrders(restaurantId);

        modelMap.addAttribute("restaurant", restaurantDTO);
        modelMap.addAttribute("menu", menuDTO);
        modelMap.addAttribute("deliveries", deliveriesDTO);
        modelMap.addAttribute("orders", ordersDTO);
        return "local_manager";
    }
}
