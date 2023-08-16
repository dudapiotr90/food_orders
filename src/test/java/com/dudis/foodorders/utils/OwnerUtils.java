package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.database.entities.OwnerEntity;

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
            .account(AccountUtils.someAccount1().withRoleId(2))
            .build();
    }

    public static Owner someOwner2() {
        return Owner.builder()
            .ownerId(1)
            .name("owner2")
            .surname("surname2")
            .account(AccountUtils.someAccount1().withEmail("anotherowner@email").withRoleId(2))
            .build();
    }

    public static Owner someOwner3() {
        return Owner.builder()
            .ownerId(1)
            .name("owner3")
            .surname("surname3")
            .account(AccountUtils.someAccount1().withEmail("owner@email").withRoleId(2))
            .build();
    }


    public static OwnerEntity someOwnerEntity1() {
        return OwnerEntity.builder()
            .ownerId(1)
            .name("owner1")
            .surname("surname1")
            .account(AccountUtils.someAccountEntity1())
            .build();
    }

    public static OwnerEntity someOwnerEntity2() {
        return OwnerEntity.builder()
            .ownerId(1)
            .name("owner2")
            .surname("surname2")
            .account(AccountUtils.someAccountEntity2())
            .build();
    }

    public static OwnerEntity someOwnerEntity3() {
        return OwnerEntity.builder()
            .ownerId(1)
            .name("owner3")
            .surname("surname3")
            .account(AccountUtils.someAccountEntity3())
            .build();
    }
    public static List<Owner> someOwners() {
        return List.of(someOwner1(), someOwner2(), someOwner3());
    }
    public static List<OwnerDTO> someOwnersDTO() {
        return List.of(someOwnerDTO1(), someOwnerDTO2(), someOwnerDTO3());
    }
    public static List<OwnerEntity> someOwnerEntities() {
        return List.of(someOwnerEntity1(), someOwnerEntity2(), someOwnerEntity3());
    }
}
