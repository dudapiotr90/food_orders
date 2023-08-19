package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.api.dtos.RestaurantForCustomerDTO;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

import static com.dudis.foodorders.services.PageableService.DEFAULT_PAGE_SIZE;

@Controller
@AllArgsConstructor
public class SearchEngineController {

    public static final String SEARCH_ENGINE = "/customer/{id}/searchEngine";
    public static final String SEARCH_ENGINE_WITH_RESULTS = "/customer/{id}/searchEngine/page/{pageNumber}";
    private final SecurityUtils securityUtils;
    private final RestaurantService restaurantService;


    @GetMapping(SEARCH_ENGINE)
    public String enterResearchEngine(
        @PathVariable(value = "id") Integer customerId,
        ModelMap modelMap,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        modelMap.addAttribute("customerId", customerId);
        modelMap.addAttribute("deliveryAddress", new DeliveryAddressDTO());
        prepareDefaultPaginatedAttributes(modelMap);
        return "search_engine";
    }

    @GetMapping(SEARCH_ENGINE_WITH_RESULTS)
    public String searchForRestaurants(
        @PathVariable(value = "id") Integer customerId,
        @PathVariable(value = "pageNumber", required = false) Integer pageNumber,
        @ModelAttribute("deliveryAddress")DeliveryAddressDTO deliveryAddress,
        @RequestParam(value = "pageSize",required = false) Integer pageSize,
        @RequestParam(value = "sortBy",required = false) String sortBy,
        @RequestParam(value = "sortHow",required = false) String sortHow,
        ModelMap modelMap,
        HttpServletRequest request
    ) {
        securityUtils.checkAccess(customerId, request);
        if (searchedWithoutParameters(deliveryAddress)) {
            Page<RestaurantForCustomerDTO> restaurants = restaurantService
                .findAllRestaurants(pageNumber,pageSize,sortBy,sortHow);
            modelMap.addAttribute("customerId", customerId);
            modelMap.addAttribute("deliveryAddress", deliveryAddress);
            preparePaginatedAttributes(modelMap,restaurants,pageNumber,sortBy,sortHow,pageSize);
        } else {
            Page<RestaurantForCustomerDTO> restaurants = restaurantService
                .findAllRestaurantsByParameters(deliveryAddress,pageNumber,pageSize,sortBy,sortHow);
            modelMap.addAttribute("customerId", customerId);
            modelMap.addAttribute("deliveryAddress", deliveryAddress);
            preparePaginatedAttributes(modelMap,restaurants,pageNumber,sortBy,sortHow,pageSize);
        }
        return "search_engine";

    }

    private void preparePaginatedAttributes(
        ModelMap modelMap,
        Page<RestaurantForCustomerDTO> restaurants,
        int pageNumber,
        String sortBy,
        String sortHow,
        Integer pageSize) {
        modelMap.addAttribute("restaurants", restaurants.getContent());
        modelMap.addAttribute("reverseSortHow", sortHow.equals("asc") ? "desc" : "asc");
        modelMap.addAttribute("sortHow", sortHow);
        modelMap.addAttribute("sortBy", sortBy);
        modelMap.addAttribute("pageSize", Objects.isNull(pageSize)? DEFAULT_PAGE_SIZE : pageSize);
        modelMap.addAttribute("currentPage", pageNumber);
        modelMap.addAttribute("totalPages", restaurants.getTotalPages());
        modelMap.addAttribute("totalSize", restaurants.getTotalElements());
    }

    private void prepareDefaultPaginatedAttributes(ModelMap modelMap) {
        modelMap.addAttribute("pageNumber", 1);
        modelMap.addAttribute("pageSize", DEFAULT_PAGE_SIZE);
        modelMap.addAttribute("sortBy", "name");
        modelMap.addAttribute("sortHow", "asc");
    }


    private boolean searchedWithoutParameters(DeliveryAddressDTO deliveryAddress) {
        return deliveryAddress.getCity().isBlank()
            && deliveryAddress.getPostalCode().isBlank()
            && deliveryAddress.getStreet().isBlank();
    }
}
