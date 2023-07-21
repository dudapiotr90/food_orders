package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.LocalType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalRequestDTO {

    String name;
    String description;
    LocalType type;
}
