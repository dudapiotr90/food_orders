package pl.dudis.foodorders.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dudis.foodorders.infrastructure.security.RegistrationRequest;
import pl.dudis.foodorders.services.dao.AccountDAO;

@Service
@AllArgsConstructor
public class DeveloperService {

    private final AccountDAO accountDAO;

    public String registerDeveloper(RegistrationRequest request) {
        return "";
    }
}
