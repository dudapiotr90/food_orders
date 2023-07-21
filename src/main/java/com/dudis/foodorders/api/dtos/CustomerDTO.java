package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    Integer customerId;
    String name;
    String surname;
    Account account;
    Set<OrderDTO> orders;
    Set<BillDTO> bills;
}
