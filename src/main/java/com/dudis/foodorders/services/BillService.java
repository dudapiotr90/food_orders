package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.services.dao.BillDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BillService {
    private final BillDAO billDAO;

    public List<Bill> findOwnerPendingBills(Integer ownerId, boolean payed) {
        return billDAO.findOwnerPendingBills(ownerId,payed);
    }

    public List<Bill> findCustomerPendingBills(Integer customerId, boolean payed) {
        return billDAO.findCustomerPendingBills(customerId, payed);
    }
}
