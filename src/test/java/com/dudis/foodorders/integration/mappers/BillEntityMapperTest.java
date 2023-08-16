package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;
import com.dudis.foodorders.infrastructure.database.mappers.BillEntityMapper;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.AssertionsUtils;
import com.dudis.foodorders.utils.BillUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class BillEntityMapperTest {

    private BillEntityMapper billEntityMapper;
    @Test
    void mapFromEntityWorksCorrectly() {
        // Given
        BillEntity expected = BillUtils.someBillEntity1();

        Bill model = billEntityMapper.mapFromEntity(expected);

        // Then
        AssertionsUtils.assertBillEquals(expected, model);

    }
    @Test
    void mapToEntityWorksCorrectly() {
        // Given
        Bill expected = BillUtils.someBill1();

        // When
        BillEntity model = billEntityMapper.mapToEntity(expected);

        // Then
        AssertionsUtils.assertBillEquals(expected, model);

    }

}