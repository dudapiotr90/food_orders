package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDTO {

    Integer deliveryAddressId;
    String city;
    String postalCode;
    String street;

    public Map<String, String> asMap() {
        Map<String,String> result = new HashMap<>();
        Optional.ofNullable(deliveryAddressId).ifPresent(value -> result.put("deliveryAddressId", value.toString()));
        Optional.ofNullable(city).ifPresent(value -> result.put("city", value));
        Optional.ofNullable(postalCode).ifPresent(value -> result.put("postalCode", value));
        Optional.ofNullable(street).ifPresent(value -> result.put("street", value));
        return result;
    }
}
