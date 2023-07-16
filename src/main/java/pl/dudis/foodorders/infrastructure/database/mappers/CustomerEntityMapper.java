package pl.dudis.foodorders.infrastructure.database.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pl.dudis.foodorders.domain.Customer;
import pl.dudis.foodorders.infrastructure.database.entities.CustomerEntity;
import pl.dudis.foodorders.infrastructure.security.ApiRole;
import pl.dudis.foodorders.infrastructure.security.Role;
import pl.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import pl.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {
    @Mapping(target = "orders",ignore = true)
    @Mapping(target = "bills",ignore = true)
    @Mapping(target = "account",ignore = true)
//    @Mapping(source = "account.role", target = "account.role.roles",qualifiedByName = "mapRoleToApiRole")
//    @Mapping(source = "account", target = "account",qualifiedByName = "mapAccountToApiRoleAccounts")
    CustomerEntity mapToEntity(Customer customer);

//    @Named("mapRoleToApiRole")
//    default Set<String> mapRoleToEntity(String role) {
//        Set<String> roles = new HashSet<>();
//        roles.add(role);
//        return roles;
//    }
    @Named("mapAccountToApiRoleAccounts")
    default Set<AccountEntity> mapAccountToApiRoleAccounts(String role) {
        Set<String> roles = new HashSet<>();
        roles.add(role);
        return null;
    }

}
