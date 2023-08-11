package com.dudis.foodorders.infrastructure.security.entity;

import com.dudis.foodorders.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountEntityMapper  {

    @Mapping(source = "roleId",target = "roleId")
    @Mapping(target = "address.account", ignore = true)
    Account mapFromEntity(AccountEntity account);
    @Mapping(target = "unlocked",ignore = true)
    @Mapping(target = "enabled",ignore = true)
    AccountEntity mapToEntity(Account account);

}
