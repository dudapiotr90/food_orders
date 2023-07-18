package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

public interface CustomerDAO {
    ConfirmationToken registerCustomer(Customer customer);
}
