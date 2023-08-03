package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import com.dudis.foodorders.infrastructure.database.mappers.CartEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.OrderItemEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.OrderItemJpaRepository;
import com.dudis.foodorders.services.dao.OrderItemDAO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderItemRepository implements OrderItemDAO {

    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderItemEntityMapper orderItemEntityMapper;
    private final CartEntityMapper cartEntityMapper;

    @Override
    public void addItemToCart(Cart cart, OrderItem item) {
        OrderItemEntity itemToAdd = orderItemEntityMapper.mapToEntity(item);
        itemToAdd.setCart(cartEntityMapper.mapToEntity(cart));
        orderItemJpaRepository.save(itemToAdd);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        OrderItemEntity item = orderItemJpaRepository.findById(orderItem.getOrderItemId())
            .orElseThrow(() -> new EntityNotFoundException("OrderItem with id: [%s] not found".formatted(orderItem.getOrderItemId())));
        item.setQuantity(orderItem.getQuantity());
        orderItemJpaRepository.save(item);
    }
}
