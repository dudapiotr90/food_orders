package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;

import java.util.List;

public class CustomerUtils {

    public static Customer someCustomer() {
        return Customer.builder()
            .name("someCustomerName")
            .surname("someCustomerSurname")
            .account(AccountUtils.someAccount1().withRoleId(3))
            .build();
    }

    public static Customer someCustomer2() {
        return Customer.builder()
            .name("anotherCustomerName")
            .surname("anotherCustomerSurname")
            .account(AccountUtils.someAccount1().withEmail("another@email").withRoleId(3))
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


    public static CustomerEntity someCustomerEntity1() {
        return CustomerEntity.builder()
            .name("someCustomerEntityName")
            .surname("someCustomerEntitySurname")
            .account(AccountUtils.someAccountEntity1())
            .build();
    }

    public static CustomerEntity someCustomerEntity2() {
        return CustomerEntity.builder()
            .name("anotherCustomerName")
            .surname("anotherCustomerSurname")
            .account(AccountUtils.someAccountEntity2())
            .build();
    }

    public static List<CustomerEntity> someCustomerEntities() {
        return List.of(someCustomerEntity1(), someCustomerEntity2());
    }
}
