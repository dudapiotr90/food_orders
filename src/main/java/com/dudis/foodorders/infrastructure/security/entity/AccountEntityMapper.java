package com.dudis.foodorders.infrastructure.security.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountEntityMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "address.account", ignore = true)
    Account mapFromEntity(AccountEntity account);

//    @Mapping(target = "address.account", ignore = true)
    @Mapping(target = "roles",ignore = true)
    @Mapping(target = "status",ignore = true)
    @Mapping(target = "unlocked",ignore = true)
    @Mapping(target = "enabled",ignore = true)
    AccountEntity mapToEntity(Account account);

}
