package pl.dudis.foodorders.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dudis.foodorders.infrastructure.security.RegistrationRequest;

@Service
@AllArgsConstructor
public class OwnerService {

    public String registerOwner(RegistrationRequest request) {
        return "";
    }
}
