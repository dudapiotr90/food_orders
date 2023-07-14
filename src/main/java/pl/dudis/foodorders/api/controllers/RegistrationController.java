package pl.dudis.foodorders.api.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import pl.dudis.foodorders.api.mappers.ApiRoleMapper;
import pl.dudis.foodorders.infrastructure.security.ApiRoleService;
import pl.dudis.foodorders.infrastructure.security.RegistrationService;

import java.util.Map;

@Controller
@AllArgsConstructor
public class RegistrationController {

    public static final String REGISTER = "/register";
    public static final String REGISTER_PROCESS = "/register/process";

    private final ApiRoleService apiRoleService;
    private final ApiRoleMapper apiRoleMapper;

    private final RegistrationService registrationService;

    @GetMapping(value = REGISTER)
    public ModelAndView registerAccount() {
        Map<String, ?> model = prepareAvailableRoles();

        return new ModelAndView("registration",model);
    }

    private Map<String, ?> prepareAvailableRoles() {
        var availableRoles = apiRoleService.findAvailableApiRoles().stream()
            .map(apiRoleMapper::map)
            .toList();
        return Map.of(
            "availableRoles",availableRoles,
            "registrationRequestDTO",RegistrationRequestDTO.builder().build()
        );
    }

//    @PostMapping(value = REGISTER_PROCESS)
}
