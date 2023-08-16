package com.dudis.foodorders.infrastructure.database.mappers;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;
import com.dudis.foodorders.utils.BillUtils;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class BillEntityMapperTest {

    private BillEntityMapper billEntityMapper = Mappers.getMapper(BillEntityMapper.class);

    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        BillEntity billEntity = BillUtils.someBillEntity1();

        // When
        Bill model = billEntityMapper.mapFromEntity(billEntity);

        // Then
    }

    @Test
    void mapToEntityWorksCorrectly() {

    }

}