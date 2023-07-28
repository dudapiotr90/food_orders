package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
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
   private BigDecimal amount;
   private Boolean payed;
   private OwnerDTO owner;
   private CustomerDTO customer;
   private OrderDTO order;
}
