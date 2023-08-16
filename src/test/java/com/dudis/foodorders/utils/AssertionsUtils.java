package com.dudis.foodorders.utils;


import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.infrastructure.database.entities.*;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionsUtils {

    public static void assertBillEquals(BillEntity expected, Bill model) {
        assertAll(
            () -> assertEquals(expected.getBillId(), model.getBillId()),
            () -> assertEquals(expected.getBillNumber(), model.getBillNumber()),
            () -> assertEquals(expected.getDateTime(), model.getDateTime()),
            () -> assertEquals(expected.getAmount(), model.getAmount()),
            () -> assertEquals(expected.getPayed(), model.getPayed()),
            () -> assertNull(model.getOwner()),
            () -> assertCustomerFromEntityEquals(expected.getCustomer(), model.getCustomer()),
            () -> assertOrderEquals(expected.getOrder(), model.getOrder())
        );
    }

    public static void assertOrderEquals(OrderEntity expected, Order model) {
        assertAll(
            () -> assertNull(model.getRestaurant()),
            () -> assertEquals(expected.getOrderId(), model.getOrderId()),
            () -> assertEquals(expected.getOrderNumber(), model.getOrderNumber()),
            () -> assertEquals(expected.getReceivedDateTime(), model.getReceivedDateTime()),
            () -> assertEquals(expected.getCompletedDateTime(), model.getCompletedDateTime()),
            () -> assertEquals(expected.getCustomerComment(), model.getCustomerComment()),
            () -> assertEquals(expected.getRealized(), model.getRealized()),
            () -> assertEquals(expected.getInProgress(), model.getInProgress()),
            () -> assertEquals(expected.getCancelTill(), model.getCancelTill()),
            () -> AssertionsUtils.assertOrderItemsFromEntityEquals(expected.getOrderItems(), model.getOrderItems()),
            () -> assertCustomerFromEntityEquals(expected.getCustomer(), model.getCustomer())
        );
    }

    public static void assertOrderItemsFromEntityEquals(Set<OrderItemEntity> expected, Set<OrderItem> model) {
        assertEquals(expected.size(), model.size());
        for (OrderItemEntity expectedItem : expected) {
            OrderItem match = model.stream()
                .filter(modelItem -> assertOrderItemEquals(expectedItem, modelItem))
                .findFirst()
                .orElse(null);
            assertNotNull(match, "expectedItem not found in model: expected: " + expectedItem);
        }
    }

    public static void assertCustomerFromEntityEquals(CustomerEntity expected, Customer model) {
        assertAll(
            () -> assertEquals(expected.getCustomerId(), model.getCustomerId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertAccountFromEntity(expected.getAccount(), model.getAccount())
        );
    }

    public static boolean assertFoodEquals(FoodEntity expected, Food model) {
        return Objects.equals(expected.getFoodId(), model.getFoodId())
            && Objects.equals(expected.getName(), model.getName())
            && Objects.equals(expected.getDescription(), model.getDescription())
            && Objects.equals(expected.getPrice(), model.getPrice())
            && Objects.equals(expected.getFoodType(), model.getFoodType());
    }

    public static void assertAccountFromEntity(AccountEntity expected, Account model) {
        assertAll(
            () -> assertEquals(expected.getAccountId(), model.getAccountId()),
            () -> assertEquals(expected.getLogin(), model.getLogin()),
            () -> assertEquals(expected.getPassword(), model.getPassword()),
            () -> assertEquals(expected.getEmail(), model.getEmail()),
            () -> assertEquals(expected.getPhone(), model.getPhone()),
            () -> assertEquals(expected.getCreationDate(), model.getCreationDate()),
            () -> assertEquals(expected.getStatus(), model.getStatus()),
            () -> assertEquals(expected.getUnlocked(), model.getUnlocked()),
            () -> assertEquals(expected.getEnabled(), model.getEnabled()),
            () -> assertEquals(expected.getRoleId(), model.getRoleId()),
            () -> assertNull(model.getAddress())
        );
    }

    public static void assertBillEquals(Bill expected, BillEntity model) {
        assertAll(
            () -> assertEquals(expected.getBillId(), model.getBillId()),
            () -> assertEquals(expected.getBillNumber(), model.getBillNumber()),
            () -> assertEquals(expected.getDateTime(), model.getDateTime()),
            () -> assertEquals(expected.getAmount(), model.getAmount()),
            () -> assertEquals(expected.getPayed(), model.getPayed()),
            () -> assertCustomerToEntityEquals(expected.getCustomer(), model.getCustomer()),
            () -> assertOrderEquals(expected.getOrder(), model.getOrder()),
            () -> assertOwnerEquals(expected.getOwner(), model.getOwner())
        );
    }
    public static void assertOwnerEquals(Owner expected, OwnerEntity model) {
        assertAll(
            () -> assertEquals(expected.getOwnerId(), model.getOwnerId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertNull(model.getAccount()),
            () -> assertNull(model.getRestaurants()),
            () -> assertNull(model.getBills())
        );
    }

    public static void assertOrderEquals(Order expected, OrderEntity model) {
        assertAll(
            () -> assertEquals(expected.getOrderId(), model.getOrderId()),
            () -> assertEquals(expected.getOrderNumber(), model.getOrderNumber()),
            () -> assertEquals(expected.getReceivedDateTime(), model.getReceivedDateTime()),
            () -> assertEquals(expected.getCompletedDateTime(), model.getCompletedDateTime()),
            () -> assertEquals(expected.getCustomerComment(), model.getCustomerComment()),
            () -> assertEquals(expected.getRealized(), model.getRealized()),
            () -> assertEquals(expected.getInProgress(), model.getInProgress()),
            () -> assertEquals(expected.getCancelTill(), model.getCancelTill()),
            () -> assertEquals(expected.getRestaurant().getRestaurantId(),model.getRestaurant().getRestaurantId()),
            () -> assertEquals(expected.getOrderItems().size(), model.getOrderItems().size()),
            () -> assertCustomerToEntityEquals(expected.getCustomer(), model.getCustomer())
        );
    }

    public static void assertOrderItemsEquals(Set<OrderItem> expected, Set<OrderItemEntity> model) {
        assertEquals(expected.size(), model.size());
        for (OrderItem expectedItem : expected) {
            OrderItemEntity match = model.stream()
                .filter(modelItem -> assertOrderItemEquals(expectedItem, modelItem))
                .findFirst()
                .orElse(null);
            assertNotNull(match, "expectedItem not found in model: expected: " + expectedItem);
        }
    }

    public static void assertRestaurantEquals(Restaurant expected, RestaurantEntity model) {
        assertAll(
            () -> assertEquals(expected.getRestaurantId(), model.getRestaurantId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getDescription(), model.getDescription()),
            () -> assertEquals(expected.getType(), model.getType()),
            () -> assertMenuEquals(expected.getMenu(), model.getMenu()),
            () -> assertNull(model.getOwner()),
            () -> assertDeliveryAddressesEquals(expected.getDeliveryAddresses(), model.getDeliveryAddresses()),
            () -> assertOrdersEquals(expected.getOrders(), model.getOrders())
        );
    }

    public static void assertOrdersEquals(Set<Order> expected, Set<OrderEntity> model) {
        assertEquals(expected.size(), model.size());
        for (Order expectedItem : expected) {
            OrderEntity match = model.stream()
                .filter(modelItem -> assertOrderEqualsReturnsBoolean(expectedItem, modelItem))
                .findFirst()
                .orElse(null);
            assertNotNull(match, "expectedItem not found in model: expected: " + expectedItem);
        }
    }

    public static boolean assertOrderEqualsReturnsBoolean(Order expected, OrderEntity model) {
        return Objects.equals(expected.getOrderId(), model.getOrderId())
            && Objects.equals(expected.getOrderNumber(), model.getOrderNumber())
            && Objects.equals(expected.getReceivedDateTime(), model.getReceivedDateTime())
            && Objects.equals(expected.getCompletedDateTime(), model.getCompletedDateTime())
            && Objects.equals(expected.getCustomerComment(), model.getCustomerComment())
            && Objects.equals(expected.getRealized(), model.getRealized())
            && Objects.equals(expected.getInProgress(), model.getInProgress())
            && Objects.equals(expected.getCancelTill(), model.getCancelTill());
    }

    public static void assertDeliveryAddressesEquals(Set<DeliveryAddress> expected, Set<DeliveryAddressEntity> model) {
        assertEquals(expected.size(), model.size());
        for (DeliveryAddress expectedItem : expected) {
            DeliveryAddressEntity match = model.stream()
                .filter(modelItem -> assertDeliveryAddressToEntityEquals(expectedItem, modelItem))
                .findFirst()
                .orElse(null);
            assertNotNull(match, "expectedItem not found in model: expected: " + expectedItem);
        }
    }

    public static void assertMenuEquals(Menu expected, MenuEntity model) {
        assertAll(
            () -> assertEquals(expected.getMenuId(), model.getMenuId()),
            () -> assertEquals(expected.getMenuName(), model.getMenuName()),
            () -> assertEquals(expected.getDescription(), model.getDescription()),
            () -> assertEquals(expected.getFoods().size(), model.getFoods().size())
        );
    }

    public static void assertCustomerToEntityEquals(Customer expected, CustomerEntity model) {
        assertAll(
            () -> assertEquals(expected.getCustomerId(), model.getCustomerId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertNull(model.getAccount())
        );
    }

    public static boolean assertOrderItemEquals(OrderItem expected, OrderItemEntity model) {
        return Objects.equals(expected.getOrderItemId(), model.getOrderItemId())
            && Objects.equals(expected.getQuantity(), model.getQuantity())
            && assertFoodEquals(expected.getFood(), model.getFood());
    }

    public static boolean assertOrderItemEquals(OrderItemEntity expected, OrderItem model) {
        return Objects.equals(expected.getOrderItemId(), model.getOrderItemId())
            && Objects.equals(expected.getQuantity(), model.getQuantity())
            && assertFoodEquals(expected.getFood(), model.getFood());
    }

    public static boolean assertFoodEquals(Food expected, FoodEntity model) {
        return Objects.equals(expected.getFoodId(), model.getFoodId())
            && Objects.equals(expected.getName(), model.getName())
            && Objects.equals(expected.getDescription(), model.getDescription())
            && Objects.equals(expected.getPrice(), model.getPrice())
            && Objects.equals(expected.getFoodType(), model.getFoodType());
    }

    public static boolean assertDeliveryAddressToEntityEquals(DeliveryAddress expected, DeliveryAddressEntity model) {
        return Objects.equals(expected.getDeliveryAddressId(), model.getDeliveryAddressId())
            && Objects.equals(expected.getCity(), model.getCity())
            && Objects.equals(expected.getPostalCode(), model.getPostalCode())
            && Objects.equals(expected.getStreet(), model.getStreet());
    }

    public static boolean assertDeliveryAddressFromEntityEquals(DeliveryAddressEntity expected, DeliveryAddress model) {
        return Objects.equals(expected.getDeliveryAddressId(), model.getDeliveryAddressId())
            && Objects.equals(expected.getCity(), model.getCity())
            && Objects.equals(expected.getPostalCode(), model.getPostalCode())
            && Objects.equals(expected.getStreet(), model.getStreet());
    }

    public static void assertCartFromEntityEquals(CartEntity expected, Cart model) {
        assertAll(
            () -> assertEquals(expected.getCartId(), model.getCartId()),
            () -> assertEquals(expected.getCustomerId(), model.getCustomer().getCustomerId()),
            () -> assertOrderItemsFromEntityEquals(expected.getOrderItems(), model.getOrderItems())
        );
    }

    public static void assertCartToEntityEquals(Cart expected, CartEntity model) {
        assertAll(
            () -> assertEquals(expected.getCartId(), model.getCartId()),
            () -> assertEquals(expected.getCustomer().getCustomerId(), model.getCustomerId()),
            () -> assertNull(model.getOrderItems())
        );
    }

    public static void assertDeveloperFromEntityEquals(DeveloperEntity expected, Developer model) {
        assertAll(
            () -> assertEquals(expected.getDeveloperId(), model.getDeveloperId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertAccountFromEntity(expected.getAccount(), model.getAccount())
        );
    }

    public static void assertDeveloperToEntityEquals(Developer expected, DeveloperEntity model) {
        assertAll(
            () -> assertEquals(expected.getDeveloperId(), model.getDeveloperId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertNull(model.getAccount())
        );
    }
}
