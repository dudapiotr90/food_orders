package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Bill;

import java.util.List;

public interface BillDAO {
    List<Bill> findOwnerPendingBills(Integer ownerId, boolean payed);

    List<Bill> findCustomerPendingBills(Integer customerId, boolean payed);
}
