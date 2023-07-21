package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.domain.Bill;
import com.dudis.foodorders.infrastructure.database.mappers.BillEntityMapper;
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
    public List<Bill> findPendingBills(Integer accountId, boolean payed) {
        return billJpaRepository.findByOwnerIdAndPayed(accountId,payed).stream()
            .map(billEntityMapper::mapFromEntity)
            .toList();
    }
}
