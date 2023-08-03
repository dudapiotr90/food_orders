package com.dudis.foodorders.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDetailsMapper {

//    @Named("mapOrderDetailsToDTO")
//    default Set<OrderDetailDTO> mapOrderDetailsToDTO(Set<OrderDetail> orderDetails) {
//        return orderDetails.stream().map(this::mapToDTO).collect(Collectors.toSet());
//    }
////
//    @Mapping(target = "order",ignore = true)
//    @Mapping(target = "cart",ignore = true)
//    OrderDetailDTO mapToDTO(OrderDetail orderDetail);
}
