package com.dudis.foodorders.services;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.services.dao.AccountDAO;

@Service
@AllArgsConstructor
public class DeveloperService {

    private final AccountDAO accountDAO;

    public ConfirmationToken registerDeveloper(RegistrationRequest request) {
        return null;
    }
}
