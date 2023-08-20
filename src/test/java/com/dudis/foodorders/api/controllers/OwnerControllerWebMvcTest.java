package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.LocalType;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.integration.support.ControllersSupport;
import com.dudis.foodorders.services.BillService;
import com.dudis.foodorders.services.OwnerService;
import com.dudis.foodorders.utils.AccountUtils;
import com.dudis.foodorders.utils.CustomerUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Set;

import static com.dudis.foodorders.api.controllers.OwnerController.*;
import static com.dudis.foodorders.utils.BillUtils.someBillsDTO;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static com.dudis.foodorders.utils.OwnerUtils.someOwnerDTO1;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OwnerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerControllerWebMvcTest extends ControllersSupport {

    private MockMvc mockMvc;
    private SecurityUtils securityUtils;
    @MockBean
    private OwnerService ownerService;
    @MockBean
    private BillService billService;

    @Test
    void getOwnerPageWorksCorrectly() throws Exception {
        // Given

        Account someAccount = AccountUtils.someAccount1();
        when(securityUtils.getLoggedInAccountId(any(HttpServletRequest.class))).thenReturn(someAccount);
        when(ownerService.findOwnerByAccountId(someAccount.getAccountId())).thenReturn(someOwnerDTO1());

        // When, Then
        mockMvc.perform(get(OWNER))
            .andExpect(status().isPermanentRedirect())
            .andExpect(redirectedUrl(OWNER + "/" + someOwnerDTO1().getOwnerId()));
    }

    @Test
    void getSpecificOwnerPageWorksCorrectly() throws Exception {
        // Given
        List<RestaurantDTO> someRestaurants = someRestaurantsDTO();
        someRestaurants.forEach(r->r.setOrders(Set.of(someOrderDTO2(),someOrderDTO1())));
        when(ownerService.findAllOwnerRestaurants(anyInt())).thenReturn(someRestaurants);
        when(billService.findOwnerPendingBills(OWNER_ID,false)).thenReturn(someBillsDTO());
        when(ownerService.findOwnerById(anyInt())).thenReturn(someOwnerDTO1());

        // When
         mockMvc.perform(get(OwnerController.OWNER_PAGE, OWNER_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("restaurants"))
            .andExpect(model().attributeExists("bills"))
            .andExpect(model().attributeExists("owner"))
            .andExpect(view().name("owner"));

    }
    @Test
    void restaurantFormPageWorksCorrectly() throws Exception {
        // Given, When

        ResultActions actions = mockMvc.perform(get(OWNER_ADD, OWNER_ID)
            .requestAttr("restaurant", new RestaurantDTO())
            .requestAttr("ownerId", OWNER_ID)
            .requestAttr("types", LocalType.values())
        );

        // Then
        actions
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("restaurant"))
            .andExpect(model().attributeExists("ownerId"))
            .andExpect(model().attributeExists("types"))
            .andExpect(view().name("local_form"));
    }

    @Test
    void addRestaurantWorksCorrectly() throws Exception {
        // Given
        RestaurantDTO someRestaurant = someRestaurantDTO4();
        doNothing().when(ownerService).addRestaurant(OWNER_ID,someRestaurant);


        // When, Then
        mockMvc.perform(post(OWNER_ADD, OWNER_ID,someRestaurant))
            .andExpect(status().isPermanentRedirect())
            .andExpect(redirectedUrl(OWNER + "/" + OWNER_ID));

    }
    @Test
    void checkOrderHistoryWorksCorrectly() throws Exception {
        // Given
        Page<OrderDTO> orders = new PageImpl<>(someListOfOrderDTO());
        orders.forEach(o->{
            o.setRestaurant(someRestaurantDTO1());
            o.setCustomer(CustomerUtils.someCustomerDTO2());
        });
        when(ownerService.findOwnerRealizedOrders(anyInt(), anyInt(), anyInt(), anyString(), anyString()))
            .thenReturn(orders);

        // When
        mockMvc.perform(get(ORDER_HISTORY, OWNER_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("owner_order_history"))
            .andExpect(model().attributeExists("ownerId"))
            .andExpect(model().attributeExists("orders"))
            .andExpect(model().attributeExists("reverseSortHow"))
            .andExpect(model().attributeExists("sortHow"))
            .andExpect(model().attributeExists("sortBy"))
            .andExpect(model().attributeExists("currentPage"))
            .andExpect(model().attributeExists("totalPages"))
            .andExpect(model().attributeExists("totalSize"));
    }
}