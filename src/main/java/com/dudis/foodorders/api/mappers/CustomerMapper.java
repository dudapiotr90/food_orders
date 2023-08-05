package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = AccountMapper.class)
public interface CustomerMapper {
    @Named("mapCustomerToDTO")
    @Mapping(target = "accountId", source = "account", qualifiedByName = "getAccountId")
    @Mapping(target = "bills", ignore = true)
    @Mapping(target = "orders", ignore = true)
    CustomerDTO mapToDTO(Customer customer);

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "bills", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Customer mapFromDTO(CustomerDTO customerDTO);
}
