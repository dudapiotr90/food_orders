package com.dudis.foodorders.utils;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.domain.*;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DTOAssertionsUtils {
    public static void assertBillToDTOEquals(Bill expected, BillDTO model) {
        assertAll(
            () -> assertEquals(expected.getBillNumber(), model.getBillNumber()),
            () -> assertEquals(expected.getPayed(), model.getPayed()),
            () -> assertEquals(expected.getAmount(), model.getAmount()),
            () -> assertNull(model.getOwner()),
            () -> assertCustomerToDTOEquals(expected.getCustomer(), model.getCustomer()),
            () -> assertOrderToDTOEquals(expected.getOrder(), model.getOrder())
        );
    }

    public static void assertOrderToDTOEquals(Order expected, OrderDTO model) {
        assertAll(
            () -> assertEquals(expected.getOrderId(), model.getOrderId()),
            () -> assertEquals(expected.getOrderNumber(), model.getOrderNumber()),
            () -> assertEquals(expected.getCustomerComment(), model.getCustomerComment()),
            () -> assertEquals(expected.getRealized(), model.getRealized()),
            () -> assertEquals(expected.getInProgress(), model.getInProgress()),
            () -> assertEquals(expected.getCancelTill(), model.getCancelTill()),
            () -> assertEquals(expected.getRestaurant().getRestaurantId(),model.getRestaurant().getRestaurantId()),
            () -> assertEquals(expected.getOrderItems().size(), model.getOrderItems().size()),
            () -> assertNull(model.getCustomer())
        );
    }

    public static void assertCustomerToDTOEquals(Customer expected, CustomerDTO model) {
        assertAll(
            () -> assertEquals(expected.getCustomerId(), model.getCustomerId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertEquals(expected.getAccount().getAccountId(), model.getAccountId()),
            () -> assertNull(model.getBills()),
            () -> assertNull(model.getOrders())
        );
    }

    public static void assertCustomerFromDTOEquals(CustomerDTO expected, Customer model) {
        assertAll(
            () -> assertEquals(expected.getCustomerId(), model.getCustomerId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertNull(model.getAccount()),
            () -> assertNull(model.getBills()),
            () -> assertNull(model.getOrders())
        );
    }
    public static void assertDeveloperToDTOEquals(Developer expected, DeveloperDTO model) {
        assertAll(
            () -> assertEquals(expected.getDeveloperId(), model.getDeveloperId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertEquals(expected.getAccount().getAccountId(), model.getAccountId())
        );
    }

    public static void assertMenuToDTOEquals(Menu expected, MenuDTO model) {
        assertAll(
            () -> assertEquals(expected.getMenuName(), model.getMenuName()),
            () -> assertEquals(expected.getDescription(), model.getMenuDescription()),
            () -> assertFoodsToDTOEquals(expected.getFoods(),model.getFoods())
        );
    }

    private static void assertFoodsToDTOEquals(Set<Food> expected, Set<FoodDTO> model) {
        assertEquals(expected.size(), model.size());
        for (Food expectedItem : expected) {
            FoodDTO match = model.stream()
                .filter(modelItem -> assertFoodToDTOEqualsReturnsTrue(expectedItem, modelItem))
                .findFirst()
                .orElse(null);
            assertNotNull(match, "expectedItem not found in model: expected: " + expectedItem);
        }
    }

    private static boolean assertFoodToDTOEqualsReturnsTrue(Food expected, FoodDTO model) {
        return Objects.equals(expected.getFoodId(), model.getFoodId())
            && Objects.equals(expected.getName(), model.getName())
            && Objects.equals(expected.getDescription(), model.getDescription())
            && Objects.equals(expected.getPrice(), model.getPrice())
            && Objects.equals(expected.getFoodImagePath(), model.getFoodImagePath())
            && Objects.equals(expected.getFoodType(), model.getFoodType());
    }

    public static void assertOrderItemToDTOEquals(OrderItem expected, OrderItemDTO model) {
        assertAll(
            () -> assertEquals(expected.getOrderItemId(), model.getOrderItemId()),
            () -> assertEquals(expected.getQuantity(), model.getQuantity()),
            () -> assertNull(model.getCart()),
            () -> assertTrue(assertFoodToDTOEqualsReturnsTrue(expected.getFood(), model.getFood())),
            () -> assertNull(model.getOrder()),
            () -> assertNull(model.getTotalCost())
        );
    }

    public static void assertOrderItemFromDTOEquals(OrderItemDTO expected, OrderItem model) {
        assertAll(
            () -> assertEquals(expected.getOrderItemId(), model.getOrderItemId()),
            () -> assertEquals(expected.getQuantity(), model.getQuantity()),
            () -> assertNull(model.getCart()),
            () -> assertNull(model.getOrder())
        );
    }

    public static void assertRestaurantToDTOEquals(Restaurant expected, RestaurantDTO model) {
        assertAll(
            () -> assertEquals(expected.getRestaurantId(), model.getRestaurantId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getDescription(), model.getDescription()),
            () -> assertEquals(expected.getType(), model.getType()),
            () -> assertNull(model.getMenuDTO()),
            () -> assertNull(model.getDeliveriesSize()),
            () -> assertNull(model.getDeliveryAddressesSize()),
            () -> assertOrdersToDTOEquals(expected.getOrders(), model.getOrders())
        );
    }

    private static void assertOrdersToDTOEquals(Set<Order> expected, Set<OrderDTO> model) {
        for (Order expectedItem : expected) {
            OrderDTO match = model.stream()
                .filter(modelItem -> assertOrderToDTOEqualsReturnsTrue(expectedItem, modelItem))
                .findFirst()
                .orElse(null);
            assertNotNull(match, "expectedItem not found in model: expected: " + expectedItem);
        }
    }

    private static boolean assertOrderToDTOEqualsReturnsTrue(Order expected, OrderDTO model) {
        return Objects.equals(expected.getOrderId(), model.getOrderId())
            && Objects.equals(expected.getOrderNumber(), model.getOrderNumber())
            && Objects.equals(expected.getCustomerComment(), model.getCustomerComment())
            && Objects.equals(expected.getRealized(), model.getRealized())
            && Objects.equals(expected.getInProgress(), model.getInProgress())
            && Objects.equals(expected.getCancelTill(), model.getCancelTill());
    }

    public static void assertRestaurantFromDTOEquals(RestaurantDTO expected, Restaurant model) {
        assertAll(
            () -> assertEquals(expected.getRestaurantId(), model.getRestaurantId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getDescription(), model.getDescription()),
            () -> assertEquals(expected.getType(), model.getType()),
            () -> assertNull(model.getMenu()),
            () -> assertNull(model.getOrders()),
            () -> assertNull(model.getOwner()),
            () -> assertNull(model.getDeliveryAddresses())
        );
    }

    public static void assertOwnerToDTOEquals(Owner expected, OwnerDTO model) {
        assertAll(
            () -> assertEquals(expected.getOwnerId(), model.getOwnerId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertEquals(expected.getAccount().getAccountId(), model.getAccountId())
        );
    }

    public static void assertOwnerFromDTOEquals(OwnerDTO expected, Owner model) {
        assertAll(
            () -> assertEquals(expected.getOwnerId(), model.getOwnerId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getSurname(), model.getSurname()),
            () -> assertNull(model.getAccount()),
            () -> assertNull(model.getRestaurants()),
            () -> assertNull(model.getBills())
        );
    }

    public static void assertRestaurantForCustomerToDTOEquals(Restaurant expected, RestaurantForCustomerDTO model) {
        assertAll(
            () -> assertEquals(expected.getRestaurantId(), model.getRestaurantId()),
            () -> assertEquals(expected.getName(), model.getName()),
            () -> assertEquals(expected.getDescription(), model.getDescription()),
            () -> assertEquals(expected.getType(), model.getType())
        );
    }
}
