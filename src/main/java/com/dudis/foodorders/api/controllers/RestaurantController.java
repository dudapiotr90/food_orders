package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.services.FoodService;
import com.dudis.foodorders.services.MenuService;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(RestaurantController.OWNER)
@AllArgsConstructor
public class RestaurantController {

    public static final String OWNER = "/owner/{id}";
    public static final String MANAGE = "/manage/{restaurantId}";
    public static final String MODIFY_MENU = MANAGE + "/modifyMenu";
    public static final String ADD_MENU_POSITION = MODIFY_MENU + "/add";
    public static final String UPDATE_MENU_POSITION = MODIFY_MENU + "/updateFood";
    public static final String DELETE_MENU_POSITION = MANAGE + "/deleteFood/{foodId}";
    public static final String MANAGE_MENU_PAGE = MANAGE + "/page/{menuPageNumber}/{deliveriesPageNumber}";
    public static final String ADD_ADDRESS = MANAGE + "/addAddress";

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final FoodService foodService;

    @GetMapping(value = MANAGE)
    public String manageRestaurant(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        Model modelMap
    ) {
        return getPaginated(ownerId, restaurantId, modelMap, 1, "foodId", "asc", 1, "deliveryAddressId", "asc");

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
        modelMap.addAttribute("foodTypes", FoodTypeDTO.values());
        return "menu";
    }

    @PostMapping(ADD_MENU_POSITION)
    public String addMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("food") FoodDTO foodDTO,
        @RequestParam("image") MultipartFile image
    ) throws IOException {
        restaurantService.addFoodToMenu(foodDTO, restaurantId, image);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @PutMapping(UPDATE_MENU_POSITION)
    public String updateMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("food") FoodDTO foodDTO,
        @RequestParam("image") MultipartFile image
    ) throws IOException {
        restaurantService.updateMenuPosition(foodDTO, restaurantId, image);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @DeleteMapping(DELETE_MENU_POSITION)
    public String removeMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @PathVariable(value = "foodId") Integer foodId
    ) {
        restaurantService.deleteFoodFromMenu(foodId);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @PostMapping(ADD_ADDRESS)
    public String addAddress(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("deliveryAddress") DeliveryAddressDTO deliveryAddressDTO
    ) {
        restaurantService.addDeliveryAddress(deliveryAddressDTO, restaurantId);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @GetMapping(value = MANAGE_MENU_PAGE)
    public String getPaginated(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        Model modelMap,
        @PathVariable(value = "menuPageNumber", required = false) int menuPageNumber,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("sortHow") String sortHow,
        @PathVariable(value = "deliveriesPageNumber", required = false) int deliveriesPageNumber,
        @RequestParam("deliverySortBy") String deliverySortBy,
        @RequestParam("deliverySortHow") String deliverySortHow
    ) {
        int pageSize = 4; // default Value
        RestaurantDTO restaurantDTO = restaurantService.findProcessingRestaurant(restaurantId);
        Page<FoodDTO> menuPage = restaurantService.getPaginatedMenu(menuPageNumber, pageSize, sortBy, sortHow, restaurantId);
        Page<DeliveryAddressDTO> deliveriesPage = restaurantService.getPaginatedDeliveries(deliveriesPageNumber, pageSize, deliverySortBy, deliverySortHow, restaurantId);
//        DeliveryAddressesDTO deliveriesDTO = restaurantService.getDeliveryAddresses(restaurantId);
        OrdersDTO ordersDTO = restaurantService.findOrders(restaurantId);
        modelMap.addAttribute("ownerId", ownerId);
        modelMap.addAttribute("restaurant", restaurantDTO);
        modelMap.addAttribute("foods", menuPage.getContent());
        modelMap.addAttribute("deliveries", deliveriesPage.getContent());
        modelMap.addAttribute("orders", ordersDTO);
        modelMap.addAttribute("deliveryAddress", new DeliveryAddressDTO());
        modelMap.addAttribute("menuPage", menuPageNumber);
        modelMap.addAttribute("deliveriesPage", deliveriesPageNumber);

        preparePaginatedAttributes(modelMap, menuPage, menuPageNumber, sortBy, sortHow, deliveriesPage, deliveriesPageNumber, deliverySortBy, deliverySortHow);

        return "local_manager";
    }


    private String restaurantManagerPortal(Integer ownerId, Integer restaurantId) {
        return String.format("redirect:/owner/%s/manage/%s", ownerId, restaurantId);
    }

    private void preparePaginatedAttributes(
        Model modelMap,
        Page<FoodDTO> menuPage,
        int pageNumber,
        String sortBy,
        String sortHow,
        Page<DeliveryAddressDTO> deliveriesPage,
        int deliveryPageNumber,
        String deliverySortBy,
        String deliverySortHow
    ) {
        modelMap.addAttribute("reverseSortHow", sortHow.equals("asc") ? "desc" : "asc");
        modelMap.addAttribute("sortHow", sortHow);
        modelMap.addAttribute("deliverySortHow", deliverySortHow);
        modelMap.addAttribute("sortBy", sortBy);
        modelMap.addAttribute("deliverySortBy", deliverySortBy);
        modelMap.addAttribute("currentPage", pageNumber);
        modelMap.addAttribute("currentDeliveryPage", deliveryPageNumber);
        modelMap.addAttribute("totalMenuPages", menuPage.getTotalPages());
        modelMap.addAttribute("totalMenuSize", menuPage.getTotalElements());
        modelMap.addAttribute("totalDeliveryPages", deliveriesPage.getTotalPages());
        modelMap.addAttribute("totalDeliverySize", deliveriesPage.getTotalElements());
    }
}
