package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.domain.Order;
import com.dudis.foodorders.infrastructure.database.entities.BillEntity;
import com.dudis.foodorders.infrastructure.database.mappers.BillEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.BillJpaRepository;
import com.dudis.foodorders.services.dao.BillDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BillRepository implements BillDAO {

    private final BillJpaRepository billJpaRepository;
    private final BillEntityMapper billEntityMapper;
    private final OrderEntityMapper orderEntityMapper;


    @Override
    public List<Bill> findOwnerPendingBills(Integer ownerId, boolean payed) {
        return billJpaRepository.findByOwnerIdAndPayed(ownerId,payed).stream()
            .map(billEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public List<Bill> findCustomerPendingBills(Integer customerId, boolean payed) {

        List<BillEntity> byCustomerIdAndPayed = billJpaRepository.findByCustomerIdAndPayed(customerId, payed);
        return byCustomerIdAndPayed.stream()
            .map(billEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public Bill saveBill(Bill bill) {
        BillEntity toSave = billEntityMapper.mapToEntity(bill);
        BillEntity saved = billJpaRepository.save(toSave);
        return billEntityMapper.mapFromEntity(saved);
    }

    @Override
    public String findIssuedBillForOrder(String orderNumber) {
        return billJpaRepository.findByOrderNumber(orderNumber);
    }

    @Override
    public void payForBill(String billNumber) {
        billJpaRepository.setPayerAsTrue(billNumber,true);
    }

    @Override
    public Bill findBillByBillNumber(String billNumber) {
        BillEntity bill = billJpaRepository.findByBillNumber(billNumber);
        return billEntityMapper.mapFromEntity(bill);
    }

    @Override
    public List<Order> findOrdersNotInProgressAndPayedAndNotRealized(Integer restaurantId, boolean inProgress, boolean payed, boolean realized) {
        return billJpaRepository.findOrdersNotInProgressAndPayedAndNotRealized(restaurantId, inProgress, payed,realized).stream()
            .map(orderEntityMapper::mapFromEntity).toList();
    }
}
