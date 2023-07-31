package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.infrastructure.database.mappers.BillEntityMapper;
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


    @Override
    public List<Bill> findOwnerPendingBills(Integer ownerId, boolean payed) {
        return billJpaRepository.findByOwnerIdAndPayed(ownerId,payed).stream()
            .map(billEntityMapper::mapFromEntity)
            .toList();
    }

    @Override
    public List<Bill> findCustomerPendingBills(Integer customerId, boolean payed) {
        return billJpaRepository.findByCustomerIdAndPayed(customerId,payed).stream()
            .map(billEntityMapper::mapFromEntity)
            .toList();
    }
}
