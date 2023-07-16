package pl.dudis.foodorders.api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import pl.dudis.foodorders.api.mappers.ApiRoleMapper;
import pl.dudis.foodorders.api.mappers.RegistrationRequestMapper;
import pl.dudis.foodorders.api.mappers.RoleMapper;
import pl.dudis.foodorders.infrastructure.security.ApiRoleService;
import pl.dudis.foodorders.infrastructure.security.RegistrationRequest;
import pl.dudis.foodorders.infrastructure.security.RegistrationService;
import pl.dudis.foodorders.infrastructure.security.Role;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class RegistrationController {

    public static final String REGISTER = "/registration";
    public static final String REGISTER_PROCESS = "/registration/process";

    private final ApiRoleService apiRoleService;
    private final ApiRoleMapper apiRoleMapper;
    private final RoleMapper roleMapper;

    private final RegistrationService registrationService;

    private final RegistrationRequestMapper registrationRequestMapper;

    @GetMapping(value = REGISTER)
    public ModelAndView registerAccountForm() {
        Map<String, ?> model = prepareAvailableRoles();

        return new ModelAndView("registration",model);
    }

    private Map<String, ?> prepareAvailableRoles() {
        var availableRoles = apiRoleService.findAvailableApiRoles().stream()
            .map(roleMapper::mapToDTO)
            .collect(Collectors.toSet());
        return Map.of(
            "availableRoles",availableRoles,
            "registrationRequestDTO",RegistrationRequestDTO.builder().build()
        );
    }

    @PostMapping(value = REGISTER)
    public String registerAccount(
        @Valid @ModelAttribute("registrationRequestDTO") RegistrationRequestDTO registrationRequestDTO,
        ModelMap model
    ) {
        RegistrationRequest request = registrationRequestMapper.mapFromDTO(registrationRequestDTO);
        return registrationService.registerAccount(request);

    }
}
