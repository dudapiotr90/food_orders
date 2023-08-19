package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.api.dtos.RestaurantForCustomerDTO;
import com.dudis.foodorders.integration.configuration.ControllersSupport;
import com.dudis.foodorders.services.RestaurantService;
import com.dudis.foodorders.utils.RestaurantUtils;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.dudis.foodorders.api.controllers.SearchEngineController.SEARCH_ENGINE;
import static com.dudis.foodorders.api.controllers.SearchEngineController.SEARCH_ENGINE_WITH_RESULTS;
import static com.dudis.foodorders.utils.DeliveryAddressUtils.someBlankDeliveryAddressDTO;
import static com.dudis.foodorders.utils.DeliveryAddressUtils.someDeliveryAddressWithCityOnlyDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SearchEngineController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class SearchEngineControllerWebMvcTest extends ControllersSupport {

    private MockMvc mockMvc;

    @MockBean
    private  RestaurantService restaurantService;

    @Test
    void enterResearchEngineWorksCorrectly() throws Exception {
        // Given, When, Then
        mockMvc.perform(MockMvcRequestBuilders.get(SEARCH_ENGINE, CUSTOMER_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("search_engine"))
            .andExpect(model().attributeExists(
                "customerId",
                "deliveryAddress",
                "pageNumber",
                "pageSize",
                "sortBy",
                "sortHow"
                )
            );
    }

    @Test
    void searchForRestaurantsWorksCorrectly() throws Exception {
        // Given
        DeliveryAddressDTO address1 = someDeliveryAddressWithCityOnlyDTO();
        DeliveryAddressDTO address2 = someBlankDeliveryAddressDTO() ;
        Integer pageNumber = 2;
        Integer pageSize = 5;
        String sortBy = "city";
        String sortHow = "asc";

        Page<RestaurantForCustomerDTO> restaurants = new PageImpl<>(RestaurantUtils.someRestaurantsForCustomerDTO());
        when(restaurantService.findAllRestaurants(pageNumber, pageSize, sortBy, sortHow))
            .thenReturn(restaurants);
        when(restaurantService.findAllRestaurantsByParameters(address1, pageNumber, pageSize, sortBy, sortHow))
            .thenReturn(restaurants);

        // When
        perform(address1, pageNumber, pageSize);
        perform(address2, pageNumber, pageSize);
    }

    @NotNull
    private void perform(DeliveryAddressDTO address, Integer pageNumber, Integer pageSize) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(SEARCH_ENGINE_WITH_RESULTS, CUSTOMER_ID, pageNumber)
                .flashAttr("deliveryAddress", address)
                .param("pageSize", pageSize.toString())
                .param("sortBy", "city")
                .param("sortHow", "asc"))
            .andExpect(status().isOk())
            .andExpect(view().name("search_engine"))
            .andExpect(getExpectedAttributes());
    }

    private ResultMatcher getExpectedAttributes() {
        return model().attributeExists(
            "customerId",
            "deliveryAddress",
            "restaurants",
            "reverseSortHow",
            "sortHow",
            "sortBy",
            "pageSize",
            "currentPage",
            "totalPages",
            "totalSize"
        );
    }
}