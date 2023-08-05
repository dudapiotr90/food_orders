package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {


    @Named("getAccountId")
    default Integer getAccountId(Account account) {
        if (Objects.isNull(account)) {
            return null;
        }
        return account.getAccountId();
    }
}
