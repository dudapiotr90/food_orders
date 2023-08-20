package com.dudis.foodorders.integration.mappers;

import com.dudis.foodorders.api.dtos.CustomerDTO;
import com.dudis.foodorders.api.mappers.CustomerMapper;
import com.dudis.foodorders.domain.Customer;
import com.dudis.foodorders.integration.configuration.EntityMappersTestConfig;
import com.dudis.foodorders.utils.CustomerUtils;
import com.dudis.foodorders.utils.DTOAssertionsUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = { EntityMappersTestConfig.class })
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerMapperTest {

    private CustomerMapper customerMapper;
    @Test
    void mapToDTOWorksCorrectly()  {
        // Given
        Customer expected = CustomerUtils.someCustomer();
        // When
        CustomerDTO model = customerMapper.mapToDTO(expected);

        // Then
        DTOAssertionsUtils.assertCustomerToDTOEquals(expected, model);

    }
    @Test
    void mapFromDTOWorksCorrectly()  {
        // Given
        CustomerDTO expected = CustomerUtils.someCustomerDTO();
        // When
        Customer model = customerMapper.mapFromDTO(expected);

        // Then
        DTOAssertionsUtils.assertCustomerFromDTOEquals(expected, model);

    }

}