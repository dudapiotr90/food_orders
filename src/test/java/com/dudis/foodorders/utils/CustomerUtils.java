package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.domain.Customer;

public class CustomerUtils {

    public static Customer someCustomer() {
        return Customer.builder()
            .name("someCustomerName")
            .surname("someCustomerSurname")
            .account(AccountUtils.someAccount().withRoleId(3))
            .build();
    }

    public static Customer someCustomer2() {
        return Customer.builder()
            .name("anotherCustomerName")
            .surname("anotherCustomerSurname")
            .account(AccountUtils.someAccount().withEmail("another@email").withRoleId(3))
            .build();
    }

    public static CustomerDTO someCustomerDTO() {
        return CustomerDTO.builder()
            .name("someCustomerName")
            .surname("someCustomerSurname")
            .accountId(4)
            .build();
    }

    public static CustomerDTO someCustomerDTO2() {
        return CustomerDTO.builder()
            .name("anotherCustomerName")
            .surname("anotherCustomerSurname")
            .accountId(7)
            .build();
    }
}
