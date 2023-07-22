package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OwnerMapper {
    @Mapping(target = "accountId", source = "account", qualifiedByName = "getAccountId")
    OwnerDTO mapToDTO(Owner owner);

    @Named("getAccountId")
    default Integer getAccountId(Account account) {
        return account.getAccountId();
    }

}
