package pl.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.dudis.foodorders.domain.Address;
import pl.dudis.foodorders.infrastructure.database.entities.AddressEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {
    @Mapping(target = "account",ignore = true)
    AddressEntity mapToEntity(Address address);
}
