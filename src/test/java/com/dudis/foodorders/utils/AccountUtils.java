package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.RegistrationRequestDTO;
import com.dudis.foodorders.api.dtos.UpdateAccountDTO;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Address;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import com.dudis.foodorders.infrastructure.security.entity.AccountManagerEntity;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Random;

@Data
@Builder
public class AccountUtils {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

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
            .roleId(3)
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
            .login("someLogin")
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

    public static RegistrationRequestDTO someRegistrationRequestWithBadEmail() {
        return RegistrationRequestDTO.builder()
            .userName("someName")
            .userSurname("someSurname")
            .userLogin("someLogin")
            .userPassword("password")
            .userConfirmPassword("password")
            .userEmail("badmail")
            .userPhone("+48 123 456 789")
            .role("OWNER")
            .userAddressCity("Warsaw")
            .userAddressPostalCode("00-001")
            .userAddressStreet("someStreet")
            .userResidenceNumber("1")
            .build();
    }

    public static RegistrationRequestDTO someRegistrationRequestWithBadPhone() {
        return RegistrationRequestDTO.builder()
            .userName("someName")
            .userSurname("someSurname")
            .userLogin("someLogin")
            .userPassword("password")
            .userConfirmPassword("password")
            .userEmail("some@mail")
            .userPhone("+48123456789")
            .role("OWNER")
            .userAddressCity("Warsaw")
            .userAddressPostalCode("00-001")
            .userAddressStreet("someStreet")
            .userResidenceNumber("1")
            .build();
    }

    public static RegistrationRequestDTO someRegistrationRequestWithBadLogin() {
        return RegistrationRequestDTO.builder()
            .userName("someName")
            .userSurname("someSurname")
            .userLogin("gin")
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

    public static AccountEntity someAccountToPersist() {
        return AccountEntity.builder()
            .email(randomEmail())
            .login(randomLogin())
            .password("password")
            .phone("+48 123 456 789")
            .creationDate(OffsetDateTime.of(2023, 2, 2, 1, 10, 0, 0, ZoneOffset.UTC))
            .enabled(true)
            .unlocked(true)
            .status(true)
            .roleId(4)
            .build();
    }

    private static String randomLogin() {
        Random random = new Random();
        int length = random.nextInt(9) + 4;

        StringBuilder randomString = generateRandomString(random, length);
        return randomString.toString();
    }

    private static String randomEmail() {
        Random random = new Random();
        int length = random.nextInt(5) + 4;

        StringBuilder randomString = generateRandomString(random, length);
        return randomString + "@" + randomString;
    }

    @NotNull
    private static StringBuilder generateRandomString(Random random, int length) {
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString;
    }

    public static AccountEntity someCustomerAccountForIntegrationTest() {
        return AccountEntity.builder()
            .email("customer@email.com")
            .login("customer")
            .password("customer")
            .phone("+48 123 456 789")
            .creationDate(OffsetDateTime.of(2000, 2, 2, 1, 10, 0, 0, ZoneOffset.UTC))
            .enabled(true)
            .unlocked(true)
            .status(true)
            .roleId(2)
            .build();
    }

    public static AccountEntity someOwnerAccountForIntegrationTest() {
        return AccountEntity.builder()
            .email("owner@email.com")
            .login("owner")
            .password("owner")
            .phone("+48 987 654 321")
            .creationDate(OffsetDateTime.of(2001, 2, 2, 1, 10, 0, 0, ZoneOffset.UTC))
            .enabled(true)
            .unlocked(true)
            .status(true)
            .roleId(3)
            .build();
    }

    public static AccountEntity someDeveloperAccountForIntegrationTest() {
        return AccountEntity.builder()
            .email("developer@email.com")
            .login("developer")
            .password("developer")
            .phone("+48 963 258 741")
            .creationDate(OffsetDateTime.of(2002, 2, 2, 1, 10, 0, 0, ZoneOffset.UTC))
            .enabled(true)
            .unlocked(true)
            .status(true)
            .roleId(4)
            .build();
    }

    public static AccountManagerEntity buildAccess(ApiRoleEntity role, AccountEntity registeredAccount) {
        return AccountManagerEntity.builder()
            .accountId(registeredAccount.getAccountId())
            .apiRoleId(role.getApiRoleId())
            .build();
    }

    public static RegistrationRequestDTO customerRequest() {
        return RegistrationRequestDTO.builder()
            .userName("Customer")
            .userSurname("Buyer")
            .userLogin("customer")
            .userPassword("customer")
            .userConfirmPassword("customer")
            .userEmail("customer@email.com")
            .userPhone("+48 123 456 789")
            .role("CUSTOMER")
            .userAddressCity("CustomerCity")
            .userAddressPostalCode("00-000")
            .userAddressStreet("CustomerStreet")
            .userResidenceNumber("5")
            .build();
    }

    public static RegistrationRequestDTO ownerRequest() {
        return RegistrationRequestDTO.builder()
            .userName("Owner")
            .userSurname("Seller")
            .userLogin("owner")
            .userPassword("owner")
            .userConfirmPassword("owner")
            .userEmail("owner@email.com")
            .userPhone("+48 987 654 321")
            .role("OWNER")
            .userAddressCity("OwnerCity")
            .userAddressPostalCode("00-000")
            .userAddressStreet("OwnerStreet")
            .userResidenceNumber("5")
            .build();
    }

    public static RegistrationRequestDTO developerRequest() {
        return RegistrationRequestDTO.builder()
            .userName("Developer")
            .userSurname("Programmer")
            .userLogin("developer")
            .userPassword("developer")
            .userConfirmPassword("developer")
            .userEmail("developer@email.com")
            .userPhone("+48 963 258 741")
            .role("DEVELOPER")
            .userAddressCity("DeveloperCity")
            .userAddressPostalCode("00-000")
            .userAddressStreet("DeveloperStreet")
            .userResidenceNumber("5")
            .build();
    }
}
