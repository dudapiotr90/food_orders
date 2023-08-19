package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.RestaurantService;
import com.dudis.foodorders.utils.AccountUtils;
import com.dudis.foodorders.utils.CustomerUtils;
import com.dudis.foodorders.utils.FoodUtils;
import com.dudis.foodorders.utils.RestaurantUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MenuController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MenuControllerWebMvcTest {

    private MockMvc mockMvc;
    @MockBean
    private SecurityUtils securityUtils;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private RestaurantService restaurantService;

    public static Stream<Arguments> showMenuWorksCorrectly() {
        return Stream.of(
            Arguments.of(5,0,null,null,MenuController.CUSTOMER_SHOW_MENU),
            Arguments.of(3,8,"name","asc",MenuController.CUSTOMER_SHOW_PAGINATED_MENU),
            Arguments.of(7,1,"foodType","desc",MenuController.CUSTOMER_SHOW_PAGINATED_MENU)
            );
    }

    @ParameterizedTest
    @MethodSource
    void showMenuWorksCorrectly(
        Integer restaurantId,
        Integer pageNumber,
        String sortBy,
        String sortHow,
        String uri
    ) throws Exception {
        // Given
        Object[] uriVariables = new Object[]{restaurantId, pageNumber};
        Page<FoodDTO> foods = new PageImpl<>(FoodUtils.someFoodsListDTO());
        Account someAccount = AccountUtils.someAccount2();
        when(securityUtils.getLoggedInAccountId(any(HttpServletRequest.class)))
            .thenReturn(someAccount);
        when(customerService.findCustomerByAccountId(someAccount.getAccountId()))
            .thenReturn(CustomerUtils.someCustomerDTO());
        when(restaurantService.findProcessingRestaurant(restaurantId))
            .thenReturn(RestaurantUtils.someRestaurantDTO1());
        when(restaurantService.getPaginatedMenu(pageNumber, 10, sortBy, sortHow, restaurantId))
            .thenReturn(foods);
        when(restaurantService.getPaginatedMenu(1, 10, "foodId", "asc", restaurantId))
            .thenReturn(foods);

        // When, Then
        if (pageNumber == 0) {
            mockMvc.perform(MockMvcRequestBuilders.get(uri, uriVariables[0]))
                .andExpect(status().isOk())
                .andExpect(view().name("menu"))
                .andExpect(getExpectedAttributes());

        } else {
            mockMvc.perform(MockMvcRequestBuilders.get(uri, uriVariables)
                    .param("sortBy",sortBy)
                    .param("sortHow",sortHow))
                .andExpect(status().isOk())
                .andExpect(view().name("menu"))
                .andExpect(getExpectedAttributes());
        }
    }

    @NotNull
    private static ResultMatcher getExpectedAttributes() {
        return model().attributeExists(
            "customer",
            "restaurant",
            "foods",
            "menuPage",
            "foodToAdd",
            "reverseSortHow",
            "sortHow",
            "sortBy",
            "currentPage",
            "totalMenuPages",
            "totalMenuSize"
        );
    }
}