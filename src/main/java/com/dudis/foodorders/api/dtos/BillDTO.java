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

   private String billNumber;
   private OffsetDateTime dateTime;
   private BigDecimal price;
   private Boolean payed;
   private OwnerDTO owner;
   private CustomerDTO customer;
}
