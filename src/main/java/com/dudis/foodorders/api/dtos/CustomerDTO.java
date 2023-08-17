package com.dudis.foodorders.api.dtos;

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

    private Integer customerId;
    private String name;
    private String surname;
    private Integer accountId;
    private Set<OrderDTO> orders;
    private Set<BillDTO> bills;

}
