package com.dudis.foodorders.api.dtos;

import lombok.*;

import java.math.BigDecimal;
@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {

   private String billNumber;
   private String dateTime;
   private Boolean payed;
   private OwnerDTO owner;
   private CustomerDTO customer;
   private OrderDTO order;
   private BigDecimal amount;
   //   private OffsetDateTime dateTime;
}
