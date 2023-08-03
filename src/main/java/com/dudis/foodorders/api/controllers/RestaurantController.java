package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
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
    public static final String MANAGE_PAGINATED_MENU_PAGE = MANAGE + "/page/{menuPageNumber}/{deliveriesPageNumber}";
    public static final String ADD_ADDRESS = MANAGE + "/addAddress";
    public static final String DELETE_ADDRESS = MANAGE + "/deleteAddress/{deliveryId}";

    private final SecurityUtils securityUtils;

    private final RestaurantService restaurantService;

    @GetMapping(value = MANAGE)
    public String manageRestaurant(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        Model modelMap,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        return getPaginated(ownerId, restaurantId, modelMap, 1, "foodId", "asc", 1, "deliveryAddressId", "asc",request);
    }

    @GetMapping(MODIFY_MENU)
    public String modifyMenu(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        ModelMap modelMap,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        modelMap.addAttribute("restaurantId", restaurantId);
        modelMap.addAttribute("ownerId", ownerId);
        modelMap.addAttribute("foodTypes", FoodTypeDTO.values());
        modelMap.addAttribute("food", new FoodDTO());
        return "menu_manager";
    }

    @PostMapping(ADD_MENU_POSITION)
    public String addMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("food") FoodDTO foodDTO,
        @RequestParam("image") MultipartFile image,
        HttpServletRequest request
    ) throws IOException {
        securityUtils.checkAccess(ownerId, request);
        restaurantService.addFoodToMenu(foodDTO, restaurantId, image);
        return modifyMenuPortal(ownerId, restaurantId);
    }

    @PutMapping(UPDATE_MENU_POSITION)
    public String updateMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("food") FoodDTO foodDTO,
        @RequestParam("image") MultipartFile image,
        HttpServletRequest request
    ) throws IOException {
        securityUtils.checkAccess(ownerId, request);
        restaurantService.updateMenuPosition(foodDTO, restaurantId, image);
        return modifyMenuPortal(ownerId, restaurantId);
    }

    @DeleteMapping(DELETE_MENU_POSITION)
    public String removeMenuPosition(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @PathVariable(value = "foodId") Integer foodId,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        restaurantService.deleteFoodFromMenu(foodId);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @DeleteMapping(DELETE_ADDRESS)
    public String deleteAddress(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @PathVariable(value = "deliveryId") Integer deliveryId,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        restaurantService.deleteAddressFromRestaurant(deliveryId);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @PostMapping(ADD_ADDRESS)
    public String addAddress(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @ModelAttribute("deliveryAddress") DeliveryAddressDTO deliveryAddressDTO,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        restaurantService.addDeliveryAddress(deliveryAddressDTO, restaurantId);
        return restaurantManagerPortal(ownerId, restaurantId);
    }

    @GetMapping(value = MANAGE_PAGINATED_MENU_PAGE)
    public String getPaginated(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        Model modelMap,
        @PathVariable(value = "menuPageNumber", required = false) int menuPageNumber,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("sortHow") String sortHow,
        @PathVariable(value = "deliveriesPageNumber", required = false) int deliveriesPageNumber,
        @RequestParam("deliverySortBy") String deliverySortBy,
        @RequestParam("deliverySortHow") String deliverySortHow,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(ownerId, request);
        int defaultPageSize = 4;
        RestaurantDTO restaurantDTO = restaurantService.findProcessingRestaurant(restaurantId);
        Page<FoodDTO> menuPage = restaurantService.getPaginatedMenu(menuPageNumber, defaultPageSize, sortBy, sortHow, restaurantId);
        Page<DeliveryAddressDTO> deliveriesPage = restaurantService.getPaginatedDeliveryAddresses(deliveriesPageNumber, defaultPageSize, deliverySortBy, deliverySortHow, restaurantId);
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

    private String restaurantManagerPortal(Integer ownerId, Integer restaurantId) {
        return String.format("redirect:/owner/%s/manage/%s", ownerId, restaurantId);
    }

    private String modifyMenuPortal(Integer ownerId, Integer restaurantId) {
        return String.format("redirect:/owner/%s/manage/%s/modifyMenu", ownerId, restaurantId);
    }
}
