package pl.dudis.foodorders.infrastructure.database.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.dudis.foodorders.domain.Account;
import pl.dudis.foodorders.infrastructure.security.entity.AccountEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountEntityMapper {

    @Mapping(target = "account.role",ignore = true)
    Account mapFromEntity(AccountEntity account);
}
