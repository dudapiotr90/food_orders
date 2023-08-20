package com.dudis.foodorders.api.controllers.rest;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import com.dudis.foodorders.infrastructure.security.RegistrationService;
import com.dudis.foodorders.services.AccountService;
import com.dudis.foodorders.utils.AccountUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.dudis.foodorders.api.controllers.rest.DeveloperEndpoint.DEV;
import static com.dudis.foodorders.api.controllers.rest.RegistrationRestController.REGISTER;
import static com.dudis.foodorders.api.controllers.rest.RegistrationRestController.UPDATE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegistrationRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RegistrationRestControllerTest {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private AccountService accountService;


    @Test
    void registerAccountWorksCorrectly() throws Exception {
        // Given
        RegistrationRequestDTO request = AccountUtils.someRegistrationRequest();
        String token = UUID.randomUUID().toString();
        when(registrationService.registerAccount(request)).thenReturn(token);

        // When, Then
        MvcResult results = mockMvc.perform(post(DEV + REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().encoding(StandardCharsets.UTF_8))
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
            .andExpect(content().string(token))
            .andReturn();

        Assertions.assertThat(results.getResponse().getContentAsString()).isEqualTo(token);
    }

    @Test
    void registerAccountThrowsBadRequestCorrectly() throws Exception {
        // Given
        RegistrationRequestDTO request = AccountUtils.someRegistrationRequest().withUserEmail("some");

        // When, Then
        mockMvc.perform(post(DEV + REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void updateAccountWorksCorrectly() throws Exception {
        // Given
        UpdateAccountDTO toUpdate = AccountUtils.someUpdateRequest2();
        UpdateAccountDTO updated = toUpdate.withNewEmail("new@email");
        when(accountService.updateAccount(toUpdate)).thenReturn(updated);

        // When, Then
        mockMvc.perform(put(DEV + UPDATE)
            .content(objectMapper.writeValueAsString(toUpdate))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.newEmail").value("new@email"))
            .andExpect(content().json(objectMapper.writeValueAsString(updated)));
    }
}