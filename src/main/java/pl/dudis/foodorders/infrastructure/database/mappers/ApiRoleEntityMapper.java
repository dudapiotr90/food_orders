package pl.dudis.foodorders.infrastructure.database.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.dudis.foodorders.infrastructure.security.ApiRole;
import pl.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiRoleEntityMapper {


    ApiRoleEntity map(ApiRole apiRole);

}
