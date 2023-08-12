package com.dudis.foodorders.api.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {

   private String billNumber;
   private OffsetDateTime dateTime;
   private BigDecimal amount;
   private Boolean payed;
   private OwnerDTO owner;
   private CustomerDTO customer;
   private OrderDTO order;
}
