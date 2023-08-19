package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class BillUtils {

    public static List<Bill> someBills() {
        return List.of(someBill1(), someBill2(), someBill3());
    }
    public static Bill someBill1() {
        return Bill.builder()
            .billId(3)
            .billNumber("21123-312312-asdas-23132-das")
            .payed(true)
            .amount(new BigDecimal("14554.145"))
            .dateTime(OffsetDateTime.of(2011,6,1,12,7,0,0, ZoneOffset.UTC))
            .order(OrderUtils.someOrder1())
            .customer(CustomerUtils.someCustomer())
            .owner(OwnerUtils.someOwner1())
            .build();
    }
    public static Bill someBill2() {
        return Bill.builder()
            .billNumber("328973-312-ah-23132-dmhnghj")
            .amount(BigDecimal.TEN)
            .build();
    }
    public static Bill someBill3() {
        return Bill.builder()
            .billNumber("267956-564-vbmcbmcv-23132-das")
            .amount(BigDecimal.TEN)
            .build();
    }
    public static Bill someBill4() {
        return Bill.builder()
            .billNumber("2671231212126-564-vbmcbmcv-23132-das")
            .payed(true)
            .amount(new BigDecimal("14554.145"))
            .dateTime(OffsetDateTime.of(2011,6,1,12,7,0,0, ZoneOffset.UTC))
            .build();
    }

    public static BillDTO someBillDTO1() {
        return BillDTO.builder()
            .billNumber("21123-312312-asdas-23132-das")
            .build();
    }


    public static BillDTO someBillDTO2() {
        return BillDTO.builder()
            .billNumber("21123-bsds-asdas-23132-das")
            .dateTime("2000-10-10")
            .payed(true)
            .owner(OwnerUtils.someOwnerDTO1())
            .customer(CustomerUtils.someCustomerDTO())
            .order(OrderUtils.someOrderDTO1())
            .amount(new BigDecimal("38.1"))
            .build();
    }


    public static BillDTO someBillDTO3() {
        return BillDTO.builder()
            .billNumber("sda-312312-asdas-23132-das")
            .dateTime("1990-10-10")
            .payed(false)
            .owner(OwnerUtils.someOwnerDTO2())
            .customer(CustomerUtils.someCustomerDTO2())
            .order(OrderUtils.someOrderDTO2())
            .amount(new BigDecimal("14.1"))
            .build();
    }

    public static List<BillDTO> someBillsDTO() {
        return List.of(someBillDTO2(), someBillDTO3());
    }

    public static List<BillEntity> someBillEntities() {
        return List.of(someBillEntity1(), someBillEntity2(), someBillEntity3());
    }

    public static BillEntity someBillEntity1() {
        return BillEntity.builder()
            .billId(3)
            .billNumber("21123-312312-asdas-23132-das")
            .payed(true)
            .amount(new BigDecimal("14554.145"))
            .dateTime(OffsetDateTime.of(2011,6,1,12,7,0,0, ZoneOffset.UTC))
            .order(OrderUtils.someOrderEntity1())
            .customer(CustomerUtils.someCustomerEntity1())
            .owner(OwnerUtils.someOwnerEntity1())
            .build();
    }
    public static BillEntity someBillEntity2() {
        return BillEntity.builder()
            .billId(87)
            .billNumber("328973-312-ah-23132-dmhnghj")
            .payed(true)
            .build();
    }
    public static BillEntity someBillEntity3() {
        return BillEntity.builder()
            .billId(65)
            .billNumber("267956-564-vbmcbmcv-23132-das")
            .payed(false)
            .build();
    }

    public static BillEntity someBillToPersist1() {
        return BillEntity.builder()
            .billNumber("21123-312312-asdas-23132-das")
            .dateTime(OffsetDateTime.of(2011,6,1,12,7,0,0, ZoneOffset.UTC))
            .payed(false)
            .amount(new BigDecimal("14554.145"))
            .owner(OwnerUtils.someOwnerEntity1().withOwnerId(null).withAccount(AccountUtils.someAccountToPersist()))
            .customer(CustomerUtils.someCustomerEntity1().withCustomerId(null).withAccount(AccountUtils.someAccountToPersist()))
            .build();
    }

    public static BillEntity someBillToPersist2() {
        return BillEntity.builder()
            .billNumber("12313-312312-ghfds-23132-das")
            .dateTime(OffsetDateTime.of(2015,1,8,4,10,0,0, ZoneOffset.UTC))
            .payed(false)
            .amount(new BigDecimal("532.12"))
            .owner(OwnerUtils.someOwnerEntity2().withOwnerId(null).withAccount(AccountUtils.someAccountToPersist()))
            .customer(CustomerUtils.someCustomerEntity2().withCustomerId(null).withAccount(AccountUtils.someAccountToPersist()))
            .build();
    }

    public static BillEntity someBillToPersist3() {
        return BillEntity.builder()
            .billNumber("sdff-312312-asdas-23132-das")
            .dateTime(OffsetDateTime.of(2020,4,12,5,15,0,0, ZoneOffset.UTC))
            .payed(false)
            .amount(new BigDecimal("1213.1"))
            .owner(OwnerUtils.someOwnerEntity3().withOwnerId(null).withAccount(AccountUtils.someAccountToPersist()))
            .customer(CustomerUtils.someCustomerEntity3().withCustomerId(null).withAccount(AccountUtils.someAccountToPersist()))
            .build();
    }
}
