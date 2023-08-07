package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.mappers.RegistrationRequestMapper;
import com.dudis.foodorders.api.mappers.RoleMapper;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.infrastructure.security.ApiRoleService;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.infrastructure.security.RegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class RegistrationController {

    public static final String REGISTRATION = "/registration";
    public static final String REGISTRATION_CONFIRM = "/registration/confirm";

    private final ApiRoleService apiRoleService;
    private final RoleMapper roleMapper;

    private final RegistrationService registrationService;

    private final RegistrationRequestMapper registrationRequestMapper;

    @GetMapping(value = REGISTRATION)
    public ModelAndView registerAccountForm() {
        Map<String, ?> model = prepareRegistrationData();

        return new ModelAndView("registration", model);
    }

    @PostMapping(value = REGISTRATION)
    public ModelAndView registerAccount(
        @Valid @ModelAttribute("registrationRequestDTO") RegistrationRequestDTO registrationRequestDTO,
        ModelMap model
    ) {
        RegistrationRequest request = registrationRequestMapper.mapFromDTO(registrationRequestDTO);
        registrationService.registerAccount(request);
        model.addAttribute("userName", registrationRequestDTO.getUserName());
        model.addAttribute("userEmail", registrationRequestDTO.getUserEmail());
        return new ModelAndView("registration_confirm",model);
    }

    @GetMapping(value = REGISTRATION_CONFIRM)
    public ModelAndView confirm(@RequestParam("token") String token) {
        Account account = registrationService.confirmToken(token);
        Map<String, ?> model = prepareConfirmationDetails(account);
        return new ModelAndView("registration_confirmed", model);
    }

    private Map<String, ?> prepareRegistrationData() {
        var availableRoles = apiRoleService.findAvailableApiRoles().stream()
            .map(roleMapper::mapToDTO)
            .collect(Collectors.toSet());
        return Map.of(
            "availableRoles", availableRoles,
            "registrationRequestDTO", RegistrationRequestDTO.builder().build()
        );
    }

    private Map<String, ?> prepareConfirmationDetails(Account account) {
        return Map.of("confirmation",buildConfirmation(account));
    }

    private RegistrationRequestDTO buildConfirmation(Account account) {
        return RegistrationRequestDTO.builder()
            .userLogin(account.getLogin())
            .userEmail(account.getEmail())
            .build();
    }
}
