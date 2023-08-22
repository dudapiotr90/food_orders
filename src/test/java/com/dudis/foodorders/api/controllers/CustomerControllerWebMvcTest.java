package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import com.dudis.foodorders.integration.support.ControllersSupport;
import com.dudis.foodorders.services.BillService;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.OrderService;
import com.dudis.foodorders.utils.AccountUtils;
import com.dudis.foodorders.utils.BillUtils;
import com.dudis.foodorders.utils.CartUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dudis.foodorders.api.controllers.CustomerController.*;
import static com.dudis.foodorders.utils.CustomerUtils.someCustomerDTO;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.someRestaurantsForCustomerDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerControllerWebMvcTest extends ControllersSupport {

    public static final String NUMBER = UUID.randomUUID().toString();
    private MockMvc mockMvc;
    private SecurityUtils securityUtils;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private BillService billService;

    @Test
    void getCustomerPageWorksCorrectly() throws Exception {
        // Given
        CustomerDTO someCustomer = someCustomerDTO();
        Account someAccount = AccountUtils.someAccount1();
        when(securityUtils.getLoggedInAccountId(any(HttpServletRequest.class))).thenReturn(someAccount);
        when(customerService.findCustomerByAccountId(someAccount.getAccountId())).thenReturn(someCustomer);

        // When, Then
        mockMvc.perform(get(CUSTOMER))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlTemplate(CUSTOMER_PAGE, someCustomer.getCustomerId()));
    }

    @Test
    void getSpecificCustomerPageWorksCorrectly() throws Exception {
        // Given
        CustomerDTO someCustomer = someCustomerDTO();
        List<BillDTO> bills = BillUtils.someBillsDTO();
        List<RestaurantForCustomerDTO> restaurants = someRestaurantsForCustomerDTO();
        OrdersDTO orders = someOrdersDTO();
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(someCustomer);
        when(billService.findCustomerPayedBills(CUSTOMER_ID, false)).thenReturn(bills);
        when(customerService.findRestaurantWithCustomerAddress(someCustomer.getAccountId()))
            .thenReturn(restaurants);
        when(securityUtils.getLoggedInAccountId(any())).thenReturn(AccountUtils.someAccount1());
        when(customerService.findCancelableOrders(CUSTOMER_ID)).thenReturn(orders);

        // When
        mockMvc.perform(get(CUSTOMER_PAGE, CUSTOMER_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("customer"))
            .andExpect(model().attributeExists(
                "customer",
                "pendingBills",
                "restaurants",
                "address",
                "orders"
            ));
    }

    @Test
    void cancelOrderWorksCorrectly() throws Exception {
        // Given
        Cart cart = CartUtils.someCart();
        when(customerService.findCartByCustomerId(CUSTOMER_ID)).thenReturn(cart);
        doNothing().when(orderService).cancelOrder(NUMBER, cart);

        // When, Then
        mockMvc.perform(put(CANCEL_ORDER, CUSTOMER_ID, NUMBER))
            .andExpect(redirectedUrlTemplate(CartController.SHOW_CART, CUSTOMER_ID))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void payForOrderWorksCorrectly() throws Exception {
        // Given
        doNothing().when(customerService).payForBill(NUMBER);

        // When, Then
        mockMvc.perform(put(PAY,CUSTOMER_ID)
                .param("billNumber", NUMBER))
            .andExpect(redirectedUrlTemplate(CUSTOMER_PAGE, CUSTOMER_ID))
            .andExpect(status().isMovedPermanently());
    }

    @Test
    void checkOrderHistoryWorksCorrectly() throws Exception {
        // Given
        Page<OrderDTO> orders = new PageImpl<>(List.of(someOrderDTO3(), someOrderDTO4()));
        String sortHow = "desc";
        String sortBy = "completedDateTime";
        Integer pageNumber = 1;

        when(customerService.findCustomerRealizedOrders(CUSTOMER_ID, pageNumber, 2, sortHow, sortBy)).thenReturn(orders);

        // When, Then
        ResultActions perform = mockMvc.perform(get(ORDER_HISTORY, CUSTOMER_ID));
            andExpect(perform);
    }

    @NotNull
    private static ResultActions andExpect(ResultActions perform) throws Exception {
        return perform.andExpect(status().isOk())
            .andExpect(view().name("customer_order_history"))
            .andExpect(model().attributeExists(
                "customerId",
                "orders",
                "reverseSortHow",
                "sortHow",
                "sortBy",
                "currentPage",
                "totalPages",
                "totalSize"
            ));
    }

    public static Stream<Arguments> getPaginatedOrderHistoryWorksCorrectly() {
        return Stream.of(
            Arguments.of(3,6,"asc","completedDateTime"),
            Arguments.of(2,4,"desc","receivedDateTime"),
            Arguments.of(8,1,"asc","realized"),
            Arguments.of(11,22,"desc","orderId")
        );
    }

    @ParameterizedTest
    @MethodSource
    void getPaginatedOrderHistoryWorksCorrectly(
        Integer customerId,
        Integer pageNumber,
        String sortHow,
        String sortBy
    ) throws Exception {
        // Given
        Page<OrderDTO> orders = new PageImpl<>(List.of(someOrderDTO3(), someOrderDTO4()));
        when(customerService.findCustomerRealizedOrders(customerId, pageNumber, 2, sortHow, sortBy))
            .thenReturn(orders);

        // When, Then
        ResultActions perform = mockMvc.perform(get(ORDER_HISTORY_PAGINATED, customerId, pageNumber)
            .param("sortHow", sortHow)
            .param("sortBy", sortBy));
        andExpect(perform);
    }
}