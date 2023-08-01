package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

import java.util.Optional;

public interface CustomerDAO {
    ConfirmationToken registerCustomer(Customer customer);

    Optional<Customer> findCustomerById(Integer id);

    Optional<Customer> findCustomerByAccountId(Integer accountId);

    Cart addCart(Integer customerId);

    Optional<Cart> findCartByCustomerId(Integer customerId);
}
