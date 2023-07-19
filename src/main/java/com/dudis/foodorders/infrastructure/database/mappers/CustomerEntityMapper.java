package com.dudis.foodorders.infrastructure.database.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {
    @Mapping(target = "orders",ignore = true)
    @Mapping(target = "bills",ignore = true)
    @Mapping(target = "account",ignore = true)
    CustomerEntity mapToEntity(Customer customer);


}
