package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Cart;
import com.dudis.foodorders.domain.OrderItem;
import com.dudis.foodorders.infrastructure.database.entities.OrderItemEntity;
import com.dudis.foodorders.infrastructure.database.mappers.CartEntityMapper;
import com.dudis.foodorders.infrastructure.database.mappers.FoodEntityMapper;
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
    private final FoodEntityMapper foodEntityMapper;

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


    private Optional<OrderItem> isFoodAlreadyInCart(Cart cart, OrderItem item) {
//        FoodEntity food = foodEntityMapper.mapToEntity(item.getFood());
        Optional<OrderItemEntity> orderItemAlreadyInCart = orderItemJpaRepository
            .findAllByFoodIdAndCartId(item.getFood().getFoodId(), cart.getCartId());

//        return orderItemAlreadyInCart.map(OrderItemEntity::getFood)
//            .filter(foodEntity -> foodEntity.getFoodId().equals(food.getFoodId()));

        return orderItemAlreadyInCart.map(orderItemEntityMapper::mapFromEntity);


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
        orderItemJpaRepository.findById(orderItemId)
            .orElseThrow(() -> new EntityNotFoundException("OrderItem with id: [%s] not found".formatted(orderItemId)));
        orderItemJpaRepository.deleteById(orderItemId);
    }

    @Override
    public OrderItem findOrderItemById(Integer id) {
        OrderItemEntity orderItem = orderItemJpaRepository.findById(id).orElse(null);
        return orderItemEntityMapper.mapFromEntity(orderItem);
    }

    @Override
    public void deleteOrderItemFromCart(OrderItem orderItem, Cart cart) {
        OrderItemEntity toDelete = orderItemEntityMapper.mapToEntity(orderItem);
        orderItemJpaRepository.deleteOrderItemFromCart(toDelete.getOrderItemId(), cart.getCartId());
    }
}
