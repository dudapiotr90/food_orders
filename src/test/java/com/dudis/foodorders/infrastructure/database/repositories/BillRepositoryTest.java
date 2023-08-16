package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;
import com.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import com.dudis.foodorders.infrastructure.database.mappers.BillEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.BillJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static com.dudis.foodorders.utils.BillUtils.*;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BillRepositoryTest {

    @InjectMocks
    private BillRepository billRepository;

    @Mock
    private BillJpaRepository billJpaRepository;
    @Mock
    private BillEntityMapper billEntityMapper;
    @Mock
    private OrderEntityMapper orderEntityMapper;

    @Test
    void findOwnerPendingBillsWorksCorrectly() {
        // Given
        List<Bill> expected = someBills();
        when(billJpaRepository.findByOwnerIdAndPayed(anyInt(), anyBoolean()))
            .thenReturn(someBillEntities());
        when(billEntityMapper.mapFromEntity(any(BillEntity.class)))
            .thenReturn(someBill1(), someBill2(), someBill3());

        // When
        List<Bill> result1 = billRepository.findOwnerPendingBills(234, true);
        List<Bill> result2 = billRepository.findOwnerPendingBills(234, false);

        // Then
        assertEquals(expected.size(),result1.size());
        assertEquals(expected.size(),result2.size());
    }

    @Test
    void findCustomerPendingBillsWorksCorrectly() {
        // Given
        List<Bill> expected = someBills();
        when(billJpaRepository.findByCustomerIdAndPayed(anyInt(), anyBoolean()))
            .thenReturn(someBillEntities());
        when(billEntityMapper.mapFromEntity(any(BillEntity.class)))
            .thenReturn(someBill1(), someBill2(), someBill3());

        // When
        List<Bill> result1 = billRepository.findCustomerPendingBills(234, true);
        List<Bill> result2 = billRepository.findCustomerPendingBills(234, false);

        // Then
        assertEquals(expected.size(),result1.size());
        assertEquals(expected.size(),result2.size());
    }
    @Test
    void saveBillWorksCorrectly() {
        // Given
        Bill expected = someBill1();
        when(billEntityMapper.mapToEntity(any(Bill.class))).thenReturn(someBillEntity1());
        when(billJpaRepository.save(someBillEntity1())).thenReturn(someBillEntity1());
        when(billEntityMapper.mapFromEntity(someBillEntity1())).thenReturn(expected);

        // When
        Bill result = billRepository.saveBill(someBill3());

        // Then
        assertEquals(expected,result);
    }

    @Test
    void findIssuedBillForOrderWorksCorrectly() {
        // Given
        String expected = UUID.randomUUID().toString();
        when(billJpaRepository.findByOrderNumber(anyString())).thenReturn(expected);
        // When
        String result = billRepository.findIssuedBillByOrderNumber(expected);
        // Then
        assertEquals(expected,result);
    }
    @Test
    void payForBillWorksCorrectly() {
        // Given
        String someOrderNumber = UUID.randomUUID().toString();
        doNothing().when(billJpaRepository).setPayedAsTrue(anyString(), anyBoolean());

        // When, Then
        billRepository.payForBill(someOrderNumber);
        verify(billJpaRepository,times(1)).setPayedAsTrue(anyString(),anyBoolean());
    }
    @Test
    void findBillByBillNumberWorksCorrectly() {
        // Given
        String someBillNumber = UUID.randomUUID().toString();
        BillEntity someBillEntity = someBillEntity1();
        Bill expected = someBill1();
        when(billJpaRepository.findByBillNumber(anyString())).thenReturn(someBillEntity);
        when(billEntityMapper.mapFromEntity(any(BillEntity.class))).thenReturn(expected);

        // When
        Bill result = billRepository.findBillByBillNumber(someBillNumber);
        // Then
        assertEquals(expected,result);
    }
    @ParameterizedTest
    @MethodSource
    void findOrdersNotInProgressAndPayedAndNotRealizedWorksCorrectly(
        Integer restaurantId,
        boolean inProgress,
        boolean payed,
        boolean realized
    ) {
        // Given
        List<Order> expected = someOrders();
        when(billJpaRepository.findOrdersNotInProgressAndPayedAndNotRealized(anyInt(), anyBoolean(), anyBoolean(), anyBoolean()))
            .thenReturn(someOrderEntities());
        when(orderEntityMapper.mapFromEntity(any(OrderEntity.class)))
            .thenReturn(someOrder1(), someOrder2());

        // When
        List<Order> result = billRepository.findOrdersNotInProgressAndPayedAndNotRealized(
            restaurantId, inProgress, payed, realized
        );
        // Then
        assertEquals(expected,result);
    }

    public static Stream<Arguments> findOrdersNotInProgressAndPayedAndNotRealizedWorksCorrectly() {
        return Stream.of(
            Arguments.of(423,true,false,true),
            Arguments.of(12,true,false,false),
            Arguments.of(9,true,true,false),
            Arguments.of(98741,false,true,true),
            Arguments.of(12,true,true,true)
        );
    }
}