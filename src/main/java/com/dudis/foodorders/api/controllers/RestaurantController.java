package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.services.FoodService;
import com.dudis.foodorders.services.MenuService;
import com.dudis.foodorders.services.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(RestaurantController.OWNER)
@AllArgsConstructor
public class RestaurantController {

    public static final String OWNER = "/owner/{id}";
    public static final String MANAGE = "/manage/{restaurantId}";
    public static final String MODIFY_MENU = "/manage/{restaurantId}/modifyMenu";
    public static final String ADD_MENU_POSITION = "/manage/{restaurantId}/modifyMenu/add";
    public static final String UPDATE_MENU_POSITION = "/manage/{restaurantId}/modifyMenu/updateFood";
    public static final String DELETE_MENU_POSITION = "/manage/{restaurantId}/deleteFood/{foodId}";
    public static final String MANAGE_PAGE = MANAGE + "/page/{pageNum}";

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final FoodService foodService;

    @GetMapping(value = MANAGE)
    public String manageRestaurant(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        Model modelMap
    ) {
        return getPaginated(ownerId, restaurantId, modelMap, 1, "id", "asc");

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
        @ModelAttribute("food") FoodDTO foodDTO
    ) {
        restaurantService.addFoodToMenu(foodDTO,restaurantId);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @PutMapping(UPDATE_MENU_POSITION)
    public String updateMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("food") FoodDTO foodDTO
    ) {
        foodService.updateMenuPosition(foodDTO);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @DeleteMapping(DELETE_MENU_POSITION)
    public String removeMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @PathVariable(value = "foodId") Integer foodId
    ) {
        foodService.deleteFood(foodId);
        return restaurantManagerPortal(ownerId, restaurantId);
    }
    @GetMapping(value = MANAGE_PAGE)
    private String getPaginated(Integer ownerId, Integer restaurantId, Model modelMap, int i, String id, String asc) {
        RestaurantDTO restaurantDTO = restaurantService.findProcessingRestaurant(restaurantId);
        MenuDTO menuDTO = restaurantService.getCurrentMenu(restaurantId);
        DeliveryAddressesDTO deliveriesDTO = restaurantService.getDeliveryAddresses(restaurantId);
        OrdersDTO ordersDTO = restaurantService.findOrders(restaurantId);

        modelMap.addAttribute("ownerId", ownerId);
        modelMap.addAttribute("restaurant", restaurantDTO);
        modelMap.addAttribute("menu", menuDTO);
        modelMap.addAttribute("deliveries", deliveriesDTO);
        modelMap.addAttribute("orders", ordersDTO);
        return "local_manager";
    }

    private static String restaurantManagerPortal(Integer ownerId, Integer restaurantId) {
        return String.format("redirect:/owner/%s/manage/%s", ownerId, restaurantId);
    }

}
