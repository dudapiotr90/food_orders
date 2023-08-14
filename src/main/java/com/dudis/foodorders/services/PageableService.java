package com.dudis.foodorders.services;

import jakarta.validation.ValidationException;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@NoArgsConstructor
public class PageableService {
    public static final int DEFAULT_PAGE_SIZE = 2;
    public Pageable preparePageable(Integer pageNumber, Integer pageSize, String sortHow, String... sortBy) {
        if (Objects.isNull(pageNumber)) {
            pageNumber = 1;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (!("asc".equalsIgnoreCase(sortHow) || "desc".equalsIgnoreCase(sortHow))) {
            throw new ValidationException("SortHow accepts only: {asc,desc}");
        }
        if (Objects.isNull(sortBy)) {
            return PageRequest.of(pageNumber - 1, pageSize);
        }
        Sort sort = sortHow.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
            Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }

}
