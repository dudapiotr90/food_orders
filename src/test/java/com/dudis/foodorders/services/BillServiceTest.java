package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.mappers.BillMapper;
import com.dudis.foodorders.api.mappers.OwnerMapper;
import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.exception.OrderException;
import com.dudis.foodorders.services.dao.BillDAO;
import com.dudis.foodorders.utils.OwnerUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static com.dudis.foodorders.utils.BillUtils.someBill1;
import static com.dudis.foodorders.utils.BillUtils.someBillDTO1;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillServiceTest {

    @InjectMocks
    private BillService billService;

    @Mock
    private BillDAO billDAO;
    @Mock
    private OrderService orderService;
    @Mock
    private BillMapper billMapper;
    @Mock
    private OwnerMapper ownerMapper;

    static Stream<Arguments> issueReceiptThrowsExceptionsCorrectly() {
        return Stream.of(
            Arguments.of(someOrder1().withRealized(true)),
            Arguments.of(someOrder1().withInProgress(false))
        );
    }

    @Test
    void findOwnerPendingBillsWorksCorrectly() {
        // Given
        Bill someBill1 = someBill1();
        Bill someBill2 = someBill1().withAmount(BigDecimal.ONE);
        BillDTO someBillDTO1 = someBillDTO1();
        BillDTO someBillDTO2 = someBillDTO1().withAmount(BigDecimal.ONE);
        List<BillDTO> expected = List.of(someBillDTO1, someBillDTO2);
        when(billDAO.findOwnerPendingBills(1, false)).thenReturn(List.of(someBill1, someBill2));
        when(billMapper.mapToDTO(someBill1)).thenReturn(someBillDTO1, someBillDTO2);
        // When
        List<BillDTO> result = billService.findOwnerPendingBills(1, false);

        // Then
        assertEquals(expected.size(), result.size());
    }

    @Test
    void findCustomerPendingBillsWorksCorrectly() {
        // Given
        Bill someBill1 = someBill1();
        Bill someBill2 = someBill1().withAmount(BigDecimal.ONE);
        BillDTO someBillDTO1 = someBillDTO1();
        BillDTO someBillDTO2 = someBillDTO1().withAmount(BigDecimal.ONE);
        List<BillDTO> expected = List.of(someBillDTO1, someBillDTO2);
        when(billDAO.findCustomerPendingBills(1, false)).thenReturn(List.of(someBill1, someBill2));
        when(billMapper.mapToDTO(someBill1)).thenReturn(someBillDTO1, someBillDTO2);
        // When
        List<BillDTO> result = billService.findCustomerPayedBills(1, false);
        // Then
        assertEquals(expected.size(), result.size());
    }

    @Test
    void issueReceiptWorksCorrectly() {
        // Given
        OrderDTO someOrderDTO = someOrderDTO1();
        OwnerDTO someOwnerDTO = OwnerUtils.someOwnerDTO1();
        Order someOrder = someOrder1();
        BillDTO someBillDTO = someBillDTO1();
        when(orderService.findOrderByOrderNumber(anyString())).thenReturn(someOrder);
        doNothing().when(orderService).setOrderAsInProgress(any(Order.class));
        when(billDAO.saveBill(any(Bill.class))).thenReturn(someBill1());
        when(billMapper.mapToDTO(someBill1())).thenReturn(someBillDTO);

        // When
        BillDTO result = billService.issueReceipt(someOrderDTO, someOwnerDTO);

        assertEquals(someBillDTO, result);
        verify(orderService, times(1)).findOrderByOrderNumber(someOrder.getOrderNumber());
        verify(ownerMapper, times(1)).mapFromDTO(someOwnerDTO);
        verify(billMapper, times(1)).mapToDTO(someBill1());
        verify(billDAO, times(1)).saveBill(any(Bill.class));

    }

    @ParameterizedTest
    @MethodSource(value = "issueReceiptThrowsExceptionsCorrectly")
    void issueReceiptThrowsExceptionsCorrectly(Order order) {
        // Given
        OrderDTO someOrderDTO = someOrderDTO1();
        OwnerDTO someOwnerDTO = OwnerUtils.someOwnerDTO1();

        String exceptionMessage1 =
            String.format("Can't issue a receipt for realized order: Order number: [%s]", order.getOrderNumber());
        String exceptionMessage2 =
            String.format("Receipt had already been issued: Receipt number: [%s]. Wait patiently for customer payment", someBill1().getBillNumber());
        when(orderService.findOrderByOrderNumber(anyString())).thenReturn(order);

        // When, Then
        if (order.getRealized()) {
            Throwable exception = assertThrows(OrderException.class, () -> billService.issueReceipt(someOrderDTO, someOwnerDTO));
            assertEquals(exceptionMessage1, exception.getMessage());
        } else if (!order.getInProgress()) {
            when(billDAO.findIssuedBillByOrderNumber(order.getOrderNumber())).thenReturn(someBill1().getBillNumber());
            Throwable exception = assertThrows(OrderException.class, () -> billService.issueReceipt(someOrderDTO, someOwnerDTO));
            assertEquals(exceptionMessage2, exception.getMessage());
        }
    }

    @Test
    void payForBillWorksCorrectly() {
        // Given
        doNothing().when(billDAO).payForBill(anyString());

        // When
        billService.payForBill(anyString());

        // Then
        verify(billDAO, times(1)).payForBill(anyString());
    }


    @Test
    void findOrdersNotInProgressAndPayedAndNotRealizedWorksCorrectly() {
        // Given
        List<Order> someOrders = List.of(someOrder1(), someOrder2());
        when(billDAO.findOrdersNotInProgressAndPayedAndNotRealized(1, false, true, false)).thenReturn(someOrders);

        // When
        List<Order> result = billService.findOrdersNotInProgressAndPayedAndNotRealized(1, false, true, false);

        // Then
        assertEquals(someOrders.size(), result.size());
    }
}