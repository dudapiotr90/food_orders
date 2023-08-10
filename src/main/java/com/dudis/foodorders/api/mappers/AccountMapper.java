package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    @Named("buildConfirmationRequest")
    default RegistrationRequestDTO buildConfirmation(Account account) {
        return RegistrationRequestDTO.builder()
            .userLogin(account.getLogin())
            .userEmail(account.getEmail())
            .build();
    }

    @Named("getAccountId")
    default Integer getAccountId(Account account) {
        if (Objects.isNull(account)) {
            return null;
        }
        return account.getAccountId();
    }

    // TODO implement mapping
//    Account updateNonNullFields(@MappingTarget Account target, Account source);
}
