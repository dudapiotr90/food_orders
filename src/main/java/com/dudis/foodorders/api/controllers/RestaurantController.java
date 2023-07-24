package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.services.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(RestaurantController.OWNER)
@AllArgsConstructor
public class RestaurantController {

    public static final String OWNER = "/owner/{id}";
    public static final String MANAGE = "/manage/{restaurantId}";
    public static final String MODIFY_MENU = "/show/{restaurantId}/modifyMenu";
    public static final String ADD_MENU_POSITION = "/show/{restaurantId}/modifyMenu/add";
    public static final String UPDATE_MENU_POSITION = "/show/{restaurantId}/modifyMenu/updateFood";

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

    // TODO create new attribute for line 75 col 34
        modelMap.addAttribute("ownerId", ownerId);
        modelMap.addAttribute("restaurant", restaurantDTO);
        modelMap.addAttribute("menu", menuDTO);
        modelMap.addAttribute("deliveries", deliveriesDTO);
        modelMap.addAttribute("orders", ordersDTO);
//        modelMap.addAttribute("updateFood", new FoodDTO());
        return "local_manager";
    }

    @GetMapping(MODIFY_MENU)
    public String modifyMenu(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        ModelMap modelMap
    ) {
        modelMap.addAttribute("food", new FoodDTO());
        modelMap.addAttribute("restaurantId", restaurantId);
        modelMap.addAttribute("ownerId", ownerId);
        return "menu";
    }

    @PostMapping(ADD_MENU_POSITION)
    public String addMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("food") FoodDTO foodDTO,
        ModelMap modelMap
    ) {
        restaurantService.addFoodToMenu(foodDTO,restaurantId);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @PutMapping(UPDATE_MENU_POSITION)
    public String updateMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("food") FoodDTO foodDTO,
        ModelMap modelMap
    ) {
        restaurantService.addFoodToMenu(foodDTO,restaurantId);
        return manageRestaurant(ownerId, restaurantId, modelMap);
    }

    private static String restaurantManagerPortal(Integer ownerId, Integer restaurantId) {
        return String.format("redirect:/owner/%s/manage/%s", ownerId, restaurantId);
    }
}
