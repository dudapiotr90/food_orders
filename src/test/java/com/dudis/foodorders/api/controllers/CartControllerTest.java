package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.integration.configuration.ControllersSupport;
import com.dudis.foodorders.services.CartService;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.RestaurantService;
import com.dudis.foodorders.utils.CustomerUtils;
import com.dudis.foodorders.utils.FoodUtils;
import com.dudis.foodorders.utils.OrderUtils;
import com.dudis.foodorders.utils.RestaurantUtils;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static com.dudis.foodorders.api.controllers.CartController.*;
import static com.dudis.foodorders.utils.OrderItemsUtils.someOrderItemDTO1;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CartController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CartControllerTest extends ControllersSupport {

    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private CustomerService customerService;

    @Test
    void updateCartPositionWorksCorrectly() throws Exception {
        // Given
        OrderItemDTO someFoodToUpdate = someOrderItemDTO1();
        doNothing().when(cartService).updateOrderItem(someFoodToUpdate);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_CART,CUSTOMER_ID)
                .flashAttr("foodToUpdate", someFoodToUpdate))
            .andExpect(status().isMovedPermanently())
            .andExpect(redirectedUrlTemplate(SHOW_CART, CUSTOMER_ID));
    }
    @Test
    void deleteFromCartWorksCorrectly() throws Exception {
        // Given
        Integer someItemId = 876;
        doNothing().when(cartService).deleteOrderItem(someItemId);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_FROM_CART,CUSTOMER_ID,someItemId))
            .andExpect(status().isMovedPermanently())
            .andExpect(redirectedUrlTemplate(SHOW_CART, CUSTOMER_ID));
    }
    @Test
    void makeAnOrderWorksCorrectly() throws Exception {
        // Given
        OrderRequestDTO request = OrderUtils.someOrderRequestDTO();
        RestaurantDTO restaurant = RestaurantUtils.someRestaurantDTO4();
        CustomerDTO customer = CustomerUtils.someCustomerDTO2();
        String issuedOrder = UUID.randomUUID().toString();
        when(restaurantService.findProcessingRestaurant(request.getRestaurantId()))
            .thenReturn(restaurant);
        when(customerService.findCustomerById(CUSTOMER_ID))
            .thenReturn(customer);
        when(cartService.issueOrder(request, restaurant, customer))
            .thenReturn(issuedOrder);

        // When, Then
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ORDER, CUSTOMER_ID)
                .flashAttr("foodsToOrder", request))
            .andExpect(status().isOk())
            .andExpect(view().name("order_issued"))
            .andExpect(model().attributeExists("customer", "orderNumber"))
            .andReturn();

        String content = result.getResponse().getContentAsString();
        Assertions.assertThat(content).contains(issuedOrder);
    }
    @Test
    void showCartWorksCorrectly() throws Exception {
        // Given
        List<OrderDetailsDTO> someItemsInCart = OrderUtils.someOrderDetailsList();
        when(customerService.getAddedFoodItems(CUSTOMER_ID)).thenReturn(someItemsInCart);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get(SHOW_CART, CUSTOMER_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("cart"))
            .andExpect(model().attributeExists("orderRequests", "customerId", "foodToUpdate", "foodsToOrder"));
    }
    @Test
    void addFoodToCartWorksCorrectly() throws Exception {
        // Given
        Integer someRestaurantId = 67;
        FoodRequestDTO request = FoodUtils.someFoodRequest();
        doNothing().when(customerService).addFoodToCart(CUSTOMER_ID, request);

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_ADD_TO_CART, CUSTOMER_ID, someRestaurantId)
                .flashAttr("foodToAdd", request))
            .andExpect(status().isMovedPermanently())
            .andExpect(redirectedUrlTemplate(MenuController.CUSTOMER_SHOW_MENU, someRestaurantId));
    }
}