package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.Order;
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
        Optional<OrderItem> foodAlreadyInCart = isFoodAlreadyInCart(cart, item);
        if (foodAlreadyInCart.isPresent()) {
            OrderItem itemToUpdate = foodAlreadyInCart.get();
            OrderItem updated = itemToUpdate.withQuantity(item.getQuantity().add(itemToUpdate.getQuantity()));
            updateOrderItem(updated);
        } else {
            OrderItemEntity itemToAdd = orderItemEntityMapper.mapToEntity(item);
            itemToAdd.setCart(cartEntityMapper.mapToEntity(cart));
            orderItemJpaRepository.save(itemToAdd);
        }
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        OrderItemEntity item = orderItemJpaRepository.findById(orderItem.getOrderItemId())
            .orElseThrow(() -> new EntityNotFoundException("OrderItem with id: [%s] not found".formatted(orderItem.getOrderItemId())));
        item.setQuantity(orderItem.getQuantity());
        orderItemJpaRepository.save(item);
    }

    @Override
    public void deleteOrderItem(Integer orderItemId) {
        orderItemJpaRepository.deleteById(orderItemId);
    }

    @Override
    public OrderItem findOrderItemById(Integer id) {
        OrderItemEntity orderItem = orderItemJpaRepository.findById(id).orElse(null);
        return orderItemEntityMapper.mapFromEntity(orderItem);
    }

    @Override
    public void deleteOrderItemFromCart(OrderItem orderItem) {
        OrderItemEntity toDelete = orderItemEntityMapper.mapToEntity(orderItem);
        orderItemJpaRepository.deleteOrderItemFromCart(toDelete.getOrderItemId());
    }

    @Override
    public void setOrder(OrderItem orderItem, Order order) {
        orderItemJpaRepository.setOrder(orderItem.getOrderItemId(), order.getOrderId());
    }

    @Override
    public void setCart(OrderItem orderItem, Cart cart) {
        orderItemJpaRepository.setCart(orderItem.getOrderItemId(), cart.getCartId());
    }

    @Override
    public void setOrderNull(OrderItem orderItem) {
        orderItemJpaRepository.setOrderNull(orderItem.getOrderItemId());
    }

    private Optional<OrderItem> isFoodAlreadyInCart(Cart cart, OrderItem item) {
        Optional<OrderItemEntity> orderItemAlreadyInCart = orderItemJpaRepository
            .findAllByFoodIdAndCartId(item.getFood().getFoodId(), cart.getCartId());
        return orderItemAlreadyInCart.map(orderItemEntityMapper::mapFromEntity);
    }
}
