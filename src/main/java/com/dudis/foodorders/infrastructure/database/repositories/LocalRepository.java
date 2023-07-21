package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Local;
import com.dudis.foodorders.infrastructure.database.mappers.LocalEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.LocalJpaRepository;
import com.dudis.foodorders.services.dao.LocalDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@AllArgsConstructor
public class LocalRepository implements LocalDAO {

    private final LocalJpaRepository localJpaRepository;
    private final LocalEntityMapper localEntityMapper;
    @Override
    public List<Local> findLocalsWhereOwnerId(Integer accountId) {
        return localJpaRepository.findAllByOwnerId(accountId).stream()
            .map(localEntityMapper::mapFromEntity)
            .toList();
    }
}
