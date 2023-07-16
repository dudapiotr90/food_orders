package pl.dudis.foodorders.infrastructure.database.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.dudis.foodorders.domain.Account;
import pl.dudis.foodorders.infrastructure.security.entity.AccountEntity;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountEntityMapper {

    @Mapping(target = "role", ignore = true)
    Account mapFromEntity(AccountEntity account);

    @Mapping(target = "address.account", ignore = true)
//    @Mapping(source = "role", target = "roles.role",qualifiedByName = "mapRoleToApiRole")
    @Mapping(target = "roles",ignore = true)
    AccountEntity mapToEntity(Account account);

    //    @Named("mapToEntity")
//    default AccountEntity mapToEntity(Account account) {
//        return AccountEntity.builder()
//            .login(account.getLogin())
//            .password(account.getPassword())
//            .email(account.getEmail())
//            .phone(account.getPhone())
//            .creationDate(account.getCreationDate())
//            .address(account.getAddress().map)
//            .build();
//    }
    @Named("mapRoleToApiRole")
    default String mapRoleToApiRole(String role) {
        return role;
    }

}
