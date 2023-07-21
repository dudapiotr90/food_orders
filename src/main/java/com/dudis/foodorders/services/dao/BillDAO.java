package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Bill;

import java.util.List;

public interface BillDAO {
    List<Bill> findPendingBills(Integer accountId, boolean payed);
}
