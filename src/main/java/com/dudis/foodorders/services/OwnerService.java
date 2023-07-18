package com.dudis.foodorders.services;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;

@Service
@AllArgsConstructor
public class OwnerService {

    public ConfirmationToken registerOwner(RegistrationRequest request) {
        return null;
    }
}
