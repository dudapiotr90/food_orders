package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.dtos.RoleDTO;
import com.dudis.foodorders.api.mappers.AccountMapper;
import com.dudis.foodorders.api.mappers.RoleMapper;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.infrastructure.security.ApiRoleService;
import com.dudis.foodorders.infrastructure.security.RegistrationService;
import com.dudis.foodorders.infrastructure.security.Role;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dudis.foodorders.api.controllers.RegistrationController.REGISTRATION;
import static com.dudis.foodorders.api.controllers.RegistrationController.REGISTRATION_CONFIRM;
import static com.dudis.foodorders.utils.AccountUtils.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RegistrationControllerWebMvcTest {

    public static final String TOKEN = UUID.randomUUID().toString();
    private MockMvc mockMvc;
    @MockBean
    private ApiRoleService apiRoleService;
    @MockBean
    private RoleMapper roleMapper;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private AccountMapper accountMapper;

    @Test
    void registerAccountFormPageWorksCorrectly() throws Exception {
         // Given,
        when(apiRoleService.findAvailableApiRoles()).thenReturn(Arrays.asList(Role.values()));
        when(roleMapper.mapToDTO(any(Role.class))).thenReturn(RoleDTO.CUSTOMER, RoleDTO.DEVELOPER, RoleDTO.OWNER);
        // When, Then
        mockMvc.perform(get(REGISTRATION))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("availableRoles", "registrationRequestDTO"))
            .andExpect(view().name("registration"));
    }

    public static Stream<Arguments> registerAccountWorksCorrectly() {
        return Stream.of(
            Arguments.of("email",someRegistrationRequestWithBadEmail()),
            Arguments.of("phone",someRegistrationRequestWithBadPhone()),
            Arguments.of("login",someRegistrationRequestWithBadLogin()),
            Arguments.of("correct",someRegistrationRequest())
        );
    }

    @ParameterizedTest
    @MethodSource
    void registerAccountWorksCorrectly(String validation, RegistrationRequestDTO request) throws Exception {
        // Given
        when(registrationService.registerAccount(request)).thenReturn(TOKEN);
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        request.asMap().forEach(params::add);

        switch (validation) {
            case "phone","email","login"-> checkValidation(params);
            case "correct" -> checkIfRegistrationWorksCorrectly(params);
        }
    }


    @Test
    void confirmWorksCorrectly() throws Exception {
         // Given
        Account someAccount = someAccount1();

        when(registrationService.confirmToken(anyString())).thenReturn(someAccount);
        when(accountMapper.buildConfirmation(someAccount)).thenReturn(someRegistrationRequest());
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token",TOKEN);

        // When, Then
        mockMvc.perform(get(REGISTRATION_CONFIRM).params(params))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("confirmation"))
            .andExpect(view().name("registration_confirmed"));
    }

    private void checkValidation(LinkedMultiValueMap<String, String> params) throws Exception {
        // Given, When, Then
        mockMvc.perform(post(REGISTRATION).params(params))
            .andExpect(status().is4xxClientError())
            .andExpect(model().attributeDoesNotExist("userName", "userEmail"))
            .andExpect(model().attributeExists("errorMessage"))
            .andExpect(view().name("error"));
    }

    private void checkIfRegistrationWorksCorrectly(LinkedMultiValueMap<String, String> params) throws Exception {
        // Given, When, Then
        mockMvc.perform(post(REGISTRATION).params(params))
            .andExpect(status().isOk())
            .andExpect(view().name("registration_confirm"))
            .andExpect(model().attributeExists("userName", "userEmail"));
    }
}