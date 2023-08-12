package com.dudis.foodorders.services.utils;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.domain.Bill;

import java.math.BigDecimal;

public class BillUtils {

    public static Bill someBill() {
        return Bill.builder()
            .billNumber("asdg123123sdfsdfa")
            .amount(BigDecimal.TEN)
            .build();
    }

    public static BillDTO someBillDTO() {
        return BillDTO.builder()
            .billNumber("asdg123123sdfsdfa")
            .build();
    }
}
