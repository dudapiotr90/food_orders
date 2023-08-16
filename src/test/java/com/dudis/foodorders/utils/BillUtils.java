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

    public static BillDTO someBillDTO() {
        return BillDTO.builder()
            .billNumber("21123-312312-asdas-23132-das")
            .build();
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

}
