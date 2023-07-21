package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {

    String billNumber;
    OffsetDateTime dateTime;
    BigDecimal price;
    Boolean payed;
    OwnerDTO owner;
    CustomerDTO customer;
}
