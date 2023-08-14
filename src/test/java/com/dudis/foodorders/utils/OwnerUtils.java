package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.domain.Owner;

import java.util.List;

public class OwnerUtils {

    public static OwnerDTO someOwnerDTO1() {
        return OwnerDTO.builder()
            .ownerId(1)
            .name("owner1")
            .surname("surname1")
            .accountId(1)
            .build();
    }
    public static OwnerDTO someOwnerDTO2() {
        return OwnerDTO.builder()
            .ownerId(1)
            .name("owner2")
            .surname("surname2")
            .accountId(2)
            .build();
    }
    public static OwnerDTO someOwnerDTO3() {
        return OwnerDTO.builder()
            .ownerId(1)
            .name("owner3")
            .surname("surname3")
            .accountId(3)
            .build();
    }

    public static Owner someOwner1() {
        return Owner.builder()
            .ownerId(1)
            .name("owner1")
            .surname("surname1")
            .build();
    }

    public static Owner someOwner2() {
        return Owner.builder()
            .ownerId(1)
            .name("owner2")
            .surname("surname2")
            .build();
    }

    public static Owner someOwner3() {
        return Owner.builder()
            .ownerId(1)
            .name("owner3")
            .surname("surname3")
            .build();
    }
    public static List<Owner> someOwners() {
        return List.of(someOwner1(), someOwner2(), someOwner3());
    }
    public static List<OwnerDTO> someOwnersDTO() {
        return List.of(someOwnerDTO1(), someOwnerDTO2(), someOwnerDTO3());
    }
}
