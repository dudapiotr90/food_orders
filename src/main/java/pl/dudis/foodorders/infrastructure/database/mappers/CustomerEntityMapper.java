package pl.dudis.foodorders.infrastructure.database.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.dudis.foodorders.domain.Customer;
import pl.dudis.foodorders.infrastructure.database.entities.CustomerEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {
    @Mapping(target = "orders",ignore = true)
    @Mapping(target = "bills",ignore = true)
//    @Mapping(target = "account.role.accounts",ignore = true)
    @Mapping(target = "account.role",ignore = true)
    CustomerEntity mapToEntity(Customer customer);

//    @Named("mapRoleToEntity")
//    default ApiRoleEntity mapRoleToEntity(ApiRole role) {
//
//    }

}
