package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = OwnerMapper.class)
public interface CustomerMapper {

    @Mapping(target = "accountId", source = "account", qualifiedByName = "getAccountId")
    @Mapping(target = "bills",ignore = true)
    @Mapping(target = "orders",ignore = true)
    CustomerDTO mapToDTO(Customer customer);

}
