package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.dtos.FoodRequestDTO;
import com.dudis.foodorders.api.dtos.RestaurantForCustomerDTO;
import com.dudis.foodorders.api.mappers.BillMapper;
import com.dudis.foodorders.api.mappers.CustomerMapper;
import com.dudis.foodorders.api.mappers.RequestMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.CustomerDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final AccountService accountService;
    private final BillService billService;
    private final DeliveryAddressService deliveryAddressService;
    private final CartService cartService;
    private final CustomerMapper customerMapper;
    private final RestaurantMapper restaurantMapper;
    private final BillMapper billMapper;
    private final RequestMapper requestMapper;

//    @Transactional
    public ConfirmationToken registerCustomer(RegistrationRequest request) {
        Account customerAccount = accountService.buildAccount(request);
        Customer customer = buildCustomer(customerAccount, request);
        return customerDAO.registerCustomer(customer);
    }

    private Customer buildCustomer(Account customerAccount, RegistrationRequest request) {
        return Customer.builder()
            .name(request.getUserName())
            .surname(request.getUserSurname())
            .account(customerAccount)
            .build();
    }

    public CustomerDTO findCustomerById(Integer id) {
        Optional<Customer> customer = customerDAO.findCustomerById(id);
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer doesn't exists");
        }
        return customerMapper.mapToDTO(customer.get());
    }

    public CustomerDTO findCustomerByAccountId(Integer accountId) {
        Optional<Customer> customer = customerDAO.findCustomerByAccountId(accountId);
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer doesn't exists");
        }
        return customerMapper.mapToDTO(customer.get());
    }

    public List<BillDTO> findPendingBills(Integer customerId) {
        return billService.findCustomerPendingBills(customerId,false).stream()
            .map(billMapper::mapToDTO)
            .toList();

    }

    public List<RestaurantForCustomerDTO> findRestaurantWithCustomerAddress(Integer accountId) {
        Account customerAccount = accountService.findCustomerAccount(accountId);
        Address address = customerAccount.getAddress();
        return deliveryAddressService.findRestaurantsIdWithAddress(address).stream()
            .map(restaurantMapper::mapToDTOForCustomer)
            .toList();
    }

    @Transactional
    public void addFoodToCart(Integer customerId, FoodRequestDTO foodToAdd) {
        OrderItem itemToAdd = requestMapper.mapFoodRequestToOrderItem(foodToAdd);
        Optional<Cart> customerCart = customerDAO.findCartByCustomerId(customerId);
        if (customerCart.isEmpty()) {
            Cart cart = customerDAO.addCart(customerId);
            cartService.addItemToCart(cart, itemToAdd);
        } else {
            cartService.addItemToCart(customerCart.get(),itemToAdd);
        }
    }
}
