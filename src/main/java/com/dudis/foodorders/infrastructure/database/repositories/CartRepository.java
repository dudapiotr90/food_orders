package com.dudis.foodorders.infrastructure.database.repositories;


import com.dudis.foodorders.infrastructure.database.mappers.CartEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderItemEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.CartJpaRepository;
import com.dudis.foodorders.services.dao.CartDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CartRepository implements CartDAO {

    private final CartJpaRepository cartJpaRepository;
    private final CartEntityMapper cartEntityMapper;
    private final OrderItemEntityMapper orderItemEntityMapper;
// TODO to remove ?
}
