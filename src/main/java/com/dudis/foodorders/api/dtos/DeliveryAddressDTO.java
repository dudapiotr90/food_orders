package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDTO {

    String city;
    String postalCode;
    String street;
}
