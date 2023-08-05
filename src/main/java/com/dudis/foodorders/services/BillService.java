package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.api.dtos.OrderDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.mappers.BillMapper;
import com.dudis.foodorders.api.mappers.OrderMapper;
import com.dudis.foodorders.api.mappers.OwnerMapper;
import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.domain.exception.OrderException;
import com.dudis.foodorders.services.dao.BillDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BillService {
    private final BillDAO billDAO;
    private final OrderService orderService;
    private final BillMapper billMapper;
    private final OwnerMapper ownerMapper;
    private final OrderMapper orderMapper;


    public List<BillDTO> findOwnerPendingBills(Integer ownerId, boolean payed) {
        return billDAO.findOwnerPendingBills(ownerId, payed).stream()
            .map(billMapper::mapToDTO)
            .toList();
    }

    public List<Bill> findCustomerPendingBills(Integer customerId, boolean payed) {
        return billDAO.findCustomerPendingBills(customerId, payed);
    }

    @Transactional
    public BillDTO issueReceipt(OrderDTO orderDTO, OwnerDTO ownerDTO) {
        Order order = orderService.findOrderByOrderNumber(orderDTO.getOrderNumber());

        checkIfBillCanBeIssued(order);

        Order updated = order.withInProgress(false);
        orderService.setOrderAsInProgress(updated);
        Owner owner = ownerMapper.mapFromDTO(ownerDTO);
        Bill bill = billDAO.saveBill(createBill(order, owner));
        return billMapper.mapToDTO(bill);
    }


    private Bill createBill(Order order, Owner owner) {
        return Bill.builder()
            .billNumber(UUID.randomUUID().toString())
            .dateTime(OffsetDateTime.now())
            .amount(calculateBillAmount(order))
            .payed(false)
            .owner(owner)
            .customer(order.getCustomer())
            .order(order)
            .build();
    }

    private BigDecimal calculateBillAmount(Order order) {
        return order.getOrderItems().stream()
            .map(orderItem -> orderItem.getFood().getPrice().multiply(orderItem.getQuantity()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void checkIfBillCanBeIssued(Order order) {
        if (order.getRealized()) {
            throw new OrderException("Can't issue a receipt for realized order: Order number: [%s]"
                .formatted(order.getOrderNumber()));
        } else if (!order.getInProgress()) {
            String issuedBill = billDAO.findIssuedBillForOrder(order.getOrderNumber());
            throw new OrderException("Receipt had already been issued: Receipt number: [%s]. Wait patiently for customer payment"
                .formatted(issuedBill));
        }
    }

    public void payForBill(String billNumber) {
        billDAO.payForBill(billNumber);
    }

    public List<Order> findOrdersNotInProgressAndPayedAndNotRealized(
        Integer restaurantId,
        boolean inProgress,
        boolean payed,
        boolean realized) {
        return billDAO.findOrdersNotInProgressAndPayedAndNotRealized(restaurantId, inProgress, payed,realized);
    }
}
