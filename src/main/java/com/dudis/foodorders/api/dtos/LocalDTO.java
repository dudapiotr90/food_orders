package com.dudis.foodorders.api.dtos;

import com.dudis.foodorders.domain.LocalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalDTO {

    Integer localId;
    String name;
    String description;
    LocalType type;

}
