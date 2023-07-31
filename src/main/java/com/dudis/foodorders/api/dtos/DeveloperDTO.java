package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperDTO {

   private Integer developerId;
   private String name;
   private String surname;
   private Integer accountId;
}
