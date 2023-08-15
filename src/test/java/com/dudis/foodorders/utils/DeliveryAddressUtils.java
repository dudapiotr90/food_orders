package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.DeliveryAddressDTO;
import com.dudis.foodorders.api.dtos.DeliveryAddressesDTO;
import com.dudis.foodorders.domain.DeliveryAddress;

import java.util.List;

public class DeliveryAddressUtils {
    public static List<DeliveryAddress> someAddresses() {
        return List.of(someDeliveryAddress1(),someDeliveryAddress2(),someDeliveryAddress3());
    }
    public static List<DeliveryAddressDTO> someAddressesDTO() {
        return List.of(someDeliveryAddressDTO1(),someDeliveryAddressDTO2(),someDeliveryAddressDTO3());
    }

    public static DeliveryAddressesDTO someDeliveryAddresses() {
        return DeliveryAddressesDTO.builder()
            .deliveries(someAddressesDTO())
            .build();
    }

    public static DeliveryAddress someDeliveryAddress1() {
        return DeliveryAddress.builder()
            .city("Warsaw")
            .postalCode("00-001")
            .street("someStreet")
            .build();
    }
    public static DeliveryAddress someDeliveryAddress2() {
        return DeliveryAddress.builder()
            .city("London")
            .postalCode("14-746")
            .street("Baker Street")
            .build();
    }
    public static DeliveryAddress someDeliveryAddress3() {
        return DeliveryAddress.builder()
            .city("Krakow")
            .postalCode("30-001")
            .street("Krakowska")
            .build();
    }

    public static DeliveryAddressDTO someDeliveryAddressDTO1() {
        return DeliveryAddressDTO.builder()
            .city("Warsaw")
            .postalCode("00-001")
            .street("someStreet")
            .build();
    }
    public static DeliveryAddressDTO someDeliveryAddressDTO2() {
        return DeliveryAddressDTO.builder()
            .city("Warsaw")
            .postalCode("00-001")
            .street("someStreet")
            .build();
    }
    public static DeliveryAddressDTO someDeliveryAddressDTO3() {
        return DeliveryAddressDTO.builder()
            .city("Warsaw")
            .postalCode("00-001")
            .street("someStreet")
            .build();
    }



    public static DeliveryAddressDTO someBlankDeliveryAddressDTO() {
        return DeliveryAddressDTO.builder()
            .city("")
            .postalCode("")
            .street("")
            .build();
    }

    public static DeliveryAddressDTO someDeliveryAddressWithCityOnlyDTO() {
        return DeliveryAddressDTO.builder()
            .city("SomeCity")
            .postalCode("")
            .street("")
            .build();
    }
    public static DeliveryAddressDTO someDeliveryAddressWithoutCityOnlyDTO() {
        return DeliveryAddressDTO.builder()
            .city("")
            .postalCode("some code")
            .street("some street")
            .build();
    }
    public static DeliveryAddressDTO someDeliveryAddressWithStreetOnlyDTO() {
        return DeliveryAddressDTO.builder()
            .city("")
            .postalCode("")
            .street("some street")
            .build();
    }
    public static DeliveryAddressDTO someDeliveryAddressWithPostalCodeOnlyDTO() {
        return DeliveryAddressDTO.builder()
            .city("")
            .postalCode("some code")
            .street("")
            .build();
    }



}
