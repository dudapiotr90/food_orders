package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchParametersDTO {
    private String sortBy;
    private String sortHow;
    private Integer resultsPerPage;
    private Integer pageNumber;
}
