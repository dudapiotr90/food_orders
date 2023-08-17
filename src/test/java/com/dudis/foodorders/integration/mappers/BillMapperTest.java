package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.api.mappers.BillMapper;
import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.integration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.BillUtils;
import com.dudis.foodorders.utils.DTOAssertionsUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.any;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class BillMapperTest {

    private BillMapper billMapper;

    @Test
    void mapToDTOWorksCorrectly() throws IOException {
        // Given
        Bill expected = BillUtils.someBill1();
        byte[] bytes = new byte[]{1};
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.readAllBytes(any(Path.class))).thenReturn(bytes);
            // When
            BillDTO model = billMapper.mapToDTO(expected);

            // Then
            DTOAssertionsUtils.assertBillToDTOEquals(expected, model);
        }
    }

}