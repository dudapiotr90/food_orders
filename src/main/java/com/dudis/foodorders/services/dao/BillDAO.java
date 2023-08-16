package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Order;

import java.util.List;

public interface BillDAO {
    List<Bill> findOwnerPendingBills(Integer ownerId, boolean payed);

    List<Bill> findCustomerPendingBills(Integer customerId, boolean payed);

    Bill saveBill(Bill bill);

    String findIssuedBillByOrderNumber(String orderNumber);

    void payForBill(String billNumber);

    Bill findBillByBillNumber(String billNumber);

    List<Order> findOrdersNotInProgressAndPayedAndNotRealized(Integer restaurantId, boolean inProgress, boolean payed, boolean realized);
}
