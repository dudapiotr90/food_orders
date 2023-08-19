package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.services.AccountService;
import com.dudis.foodorders.services.CustomerService;
import com.dudis.foodorders.services.OwnerService;
import com.dudis.foodorders.utils.OwnerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.dudis.foodorders.api.controllers.rest.DeveloperEndpoint.DEV;
import static com.dudis.foodorders.api.controllers.rest.StatisticsRestController.*;
import static com.dudis.foodorders.utils.CustomerUtils.someCustomerDTO;
import static com.dudis.foodorders.utils.CustomerUtils.someCustomerDTO2;
import static com.dudis.foodorders.utils.OwnerUtils.someOwnerDTO1;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StatisticsRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class StatisticsRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private  AccountService accountService;
    @MockBean
    private  CustomerService customerService;
    @MockBean
    private OwnerService ownerService;

    @Test
    void findHowManyUsersUseApplicationWorksCorrectly() throws Exception {
        // Given, When, Then
        when(accountService.countAllAccounts()).thenReturn(5L);

        mockMvc.perform(get(DEV+USER_ACCOUNTS))
            .andExpect(status().isOk())
            .andExpect(content().string("5"));
    }

    @Test
    void findAllCustomersWorksCorrectly() throws Exception {
        // Given
        String sortBy = "name";
        String sortHow = "asc";
        Integer pageSize =1;
        Integer pageNumber = 2;
        Page<CustomerDTO> customers = new PageImpl<>(List.of(someCustomerDTO(), someCustomerDTO2()));
        when(customerService.findAllCustomers(sortBy, sortHow, pageSize, pageNumber))
            .thenReturn(customers);

        // When, Then
        mockMvc.perform(get(DEV+CUSTOMERS)
            .param("sortBy",sortBy)
            .param("sortHow",sortHow)
            .param("pageSize",pageSize.toString())
            .param("pageNumber",pageNumber.toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isNotEmpty());
    }

    @Test
    void findCustomerWorksCorrectly() throws Exception {
        // Given
        Integer someId = 5;
        when(customerService.findCustomerById(5)).thenReturn(someCustomerDTO());

        // When, Then
        mockMvc.perform(get(DEV + CUSTOMER, someId))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(someCustomerDTO())))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void findAllOwnersWorksCorrectly() throws Exception {
        // Given
        String sortBy = "name";
        String sortHow = "asc";
        Integer pageSize =2;
        Integer pageNumber = 1;
        Page<OwnerDTO> owners = new PageImpl<>(OwnerUtils.someOwnersDTO());
        when(ownerService.findAllOwners(sortBy, sortHow, pageSize, pageNumber))
            .thenReturn(owners);

        // When, Then
        mockMvc.perform(get(DEV+OWNERS)
                .param("sortBy",sortBy)
                .param("sortHow",sortHow)
                .param("pageSize",pageSize.toString())
                .param("pageNumber",pageNumber.toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isNotEmpty());
    }
    @Test
    void findOwnerWorksCorrectly() throws Exception {
        // Given
        Integer someId = 5;
        when(ownerService.findOwnerById(5)).thenReturn(someOwnerDTO1());

        // When, Then
        mockMvc.perform(get(DEV + OWNER, someId))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(someOwnerDTO1())))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}