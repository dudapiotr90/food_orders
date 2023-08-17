package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@Builder
public class AccountUtils {

    public static Account someAccount1() {
        return Account.builder()
            .accountId(1)
            .login("someLogin")
            .password("password")
            .email("some@mail")
            .phone("+48 123 456 789")
            .creationDate(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .address(someAddress())
            .roleId(1)
            .build();
    }


    public static Account someAccount2() {
        return Account.builder()
            .accountId(5)
            .login("someLogin")
            .password("password")
            .email("some@mail")
            .phone("+48 123 456 789")
            .creationDate(OffsetDateTime.of(2023, 8, 8, 6, 30, 0, 0, ZoneOffset.UTC))
            .address(someAddress())
            .enabled(true)
            .unlocked(true)
            .roleId(1)
            .build();
    }

    public static Address someAddress() {
        return Address.builder()
            .addressId(1)
            .city("Warsaw")
            .postalCode("00-001")
            .street("someStreet")
            .residenceNumber("1")
            .build();
    }

    public static RegistrationRequestDTO someRegistrationRequest() {
        return RegistrationRequestDTO.builder()
            .userName("someName")
            .userSurname("someSurname")
            .userLogin("someLogin")
            .userPassword("password")
            .userConfirmPassword("password")
            .userEmail("some@mail")
            .userPhone("+48 123 456 789")
            .role("OWNER")
            .userAddressCity("Warsaw")
            .userAddressPostalCode("00-001")
            .userAddressStreet("someStreet")
            .userResidenceNumber("1")
            .build();
    }

    public static UpdateAccountDTO someUpdateRequest() {
        return UpdateAccountDTO.builder()
            .userEmail("some@mail")
            .build();
    }

    public static UpdateAccountDTO someUpdateRequest2() {
        return UpdateAccountDTO.builder()
            .userEmail("some@mail")
            .newUserPhone("+48 123 456 789")
            .newUserAddressCity("Warsaw")
            .newUserAddressPostalCode("00-001")
            .newUserAddressStreet("someStreet")
            .newUserResidenceNumber("1")
            .build();
    }

    public static AccountEntity someAccountEntity1() {
        return AccountEntity.builder()
            .email("empty@email")
            .accountId(1)
            .password("another password")
            .phone("+48 123 741 147")
            .creationDate(OffsetDateTime.of(2022, 7, 9, 1, 11, 0, 0, ZoneOffset.UTC))
            .status(true)
            .enabled(true)
            .unlocked(true)
            .roleId(4)
            .build();
    }

    public static AccountEntity someAccountEntity2() {
        return AccountEntity.builder()
            .email("some@mail")
            .accountId(5)
            .roleId(4)
            .status(true)
            .roleId(2)
            .build();
    }
    public static AccountEntity someAccountEntity3() {
        return AccountEntity.builder()
            .email("another@email")
            .accountId(787)
            .password("password")
            .phone("+48 123 456 789")
            .creationDate(OffsetDateTime.of(2023, 2, 2, 1, 10, 0, 0, ZoneOffset.UTC))
            .enabled(true)
            .unlocked(true)
            .roleId(4)
            .build();
    }
}
