package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.infrastructure.database.entities.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = {OrderItemEntityMapper.class})
public interface CartEntityMapper {

    @Mapping(source = "orderItems",target = "orderItems",qualifiedByName ="mapOrderItemsFromEntity")
    @Mapping(source = "customerId",target = "customer",qualifiedByName = "mapFromId")
    Cart mapFromEntity(CartEntity cart);

    @Mapping(target = "orderItems",ignore = true)
    @Mapping(source = "customer",target = "customerId",qualifiedByName = "mapToId")
    CartEntity mapToEntity(Cart cartToSave);

    @Named("mapFromId")
    default Customer mapFromId(Integer customerId) {
        return Customer.builder().customerId(customerId).build();
    }
    @Named("mapToId")
    default Integer mapFromId(Customer customer) {
        return customer.getCustomerId();
    }
}
