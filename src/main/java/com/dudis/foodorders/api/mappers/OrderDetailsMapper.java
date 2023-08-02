package com.dudis.foodorders.api.mappers;

import com.dudis.foodorders.api.dtos.OrderDetailDTO;
import com.dudis.foodorders.domain.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

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
