package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {
    @Named("mapCustomerToEntity")
    @Mapping(target = "bills",ignore = true)
    @Mapping(target = "account",ignore = true)
    @Mapping(target = "cart",ignore = true)
    @Mapping(target = "orders",ignore = true)
    CustomerEntity mapToEntity(Customer customer);
    @Named("mapCustomerFromEntity")
    @Mapping(target = "account.address",ignore = true)
    @Mapping(target = "bills",ignore = true)
    @Mapping(target = "cart",ignore = true)
    @Mapping(target = "orders",ignore = true)
    Customer mapFromEntity(CustomerEntity customerEntity);
}
