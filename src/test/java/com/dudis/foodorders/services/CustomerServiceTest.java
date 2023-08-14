package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.api.mappers.CustomerMapper;
import com.dudis.foodorders.api.mappers.OrderMapper;
import com.dudis.foodorders.api.mappers.RequestMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.CustomerDAO;
import com.dudis.foodorders.utils.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dudis.foodorders.utils.AccountUtils.someAccount;
import static com.dudis.foodorders.utils.AccountUtils.someRegistrationRequest;
import static com.dudis.foodorders.utils.CartUtils.someCart;
import static com.dudis.foodorders.utils.CustomerUtils.*;
import static com.dudis.foodorders.utils.CustomerUtils.someCustomer;
import static com.dudis.foodorders.utils.CustomerUtils.someCustomerDTO;
import static com.dudis.foodorders.utils.FoodUtils.someFoodRequest;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDAO customerDAO;
    @Mock
    private AccountService accountService;
    @Mock
    private BillService billService;
    @Mock
    private DeliveryAddressService deliveryAddressService;
    @Mock
    private CartService cartService;
    @Mock
    private OrderItemService orderItemService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private OrderRequestService orderRequestService;
    @Mock
    private PageableService pageableService;
    @Mock
    private OrderService orderService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private CustomerMapper customerMapper;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private RequestMapper requestMapper;

    @Test
    void registerCustomerWorksCorrectly() {
        // Given
        ConfirmationToken expected = TokenUtils.someToken();
        RegistrationRequest someRequest = someRegistrationRequest();
        Account someAccount = someAccount();
        when(accountService.buildAccount(someRequest)).thenReturn(someAccount);
        when(customerDAO.registerCustomer(any(Customer.class))).thenReturn(expected);
        // When
        ConfirmationToken result = customerService.registerCustomer(someRequest);

        // Then
        verify(accountService, times(1)).buildAccount(someRequest);
        verify(customerDAO, times(1)).registerCustomer(any(Customer.class));
        assertEquals(expected, result);
    }

    @Test
    void findCustomerByIdWorksCorrectly() {
        // Given
        Customer customer1 = someCustomer();
        CustomerDTO expected = someCustomerDTO();
        String expectedMessage = "Customer doesn't exists";
        when(customerDAO.findCustomerById(anyInt())).thenReturn(Optional.of(customer1));
        when(customerDAO.findCustomerById(5)).thenReturn(Optional.empty());
        when(customerMapper.mapToDTO(customer1)).thenReturn(expected);

        // When
        CustomerDTO result = customerService.findCustomerById(345);
        Throwable exception = assertThrows(NotFoundException.class, () -> customerService.findCustomerById(5));

        // Then
        assertEquals(expected, result);
        assertEquals(expectedMessage, exception.getMessage());

    }


    @Test
    void findCustomerByAccountIdWorksCorrectly() {
        // Given
        Customer customer1 = someCustomer().withAccount(someAccount());
        CustomerDTO expected = someCustomerDTO();
        String expectedMessage = "Customer doesn't exists";
        when(customerDAO.findCustomerByAccountId(anyInt())).thenReturn(Optional.of(customer1));
        when(customerDAO.findCustomerByAccountId(3)).thenReturn(Optional.empty());
        when(customerMapper.mapToDTO(any(Customer.class))).thenReturn(expected);

        // When
        CustomerDTO result = customerService.findCustomerByAccountId(34);
        Throwable exception = assertThrows(NotFoundException.class,
            () -> customerService.findCustomerByAccountId(3));

        // Then
        assertEquals(expected, result);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void findRestaurantWithCustomerAddressWorksCorrectly() {
        // Given
        Account someAccount = someAccount();
        when(accountService.findCustomerAccount(anyInt())).thenReturn(someAccount);
        List<Restaurant> expected = someRestaurants();
        when(deliveryAddressService.findRestaurantsWithAddress(someAccount.getAddress()))
            .thenReturn(expected);
        when(restaurantMapper.mapToDTOForCustomer(any(Restaurant.class)))
            .thenReturn(RestaurantUtils.someRestaurantForCustomerDTO1(), RestaurantUtils.someRestaurantForCustomerDTO2(), RestaurantUtils.someRestaurantForCustomerDTO3());

        // When
        List<RestaurantForCustomerDTO> result = customerService.findRestaurantWithCustomerAddress(someAccount.getAccountId());

        // Then
        assertEquals(expected.size(), result.size());
        verify(restaurantMapper, times(expected.size())).mapToDTOForCustomer(any(Restaurant.class));
    }

    @Test
    void addFoodToCartWhenCartDoesntExistWorksCorrectly() {
        // Given
        Integer someId = 32;
        FoodRequestDTO someFoodRequest = someFoodRequest();
        Cart someCart = someCart();
        OrderItem someOrderItem = OrderItemsUtils.someOrderItem1();
        when(requestMapper.mapFoodRequestToOrderItem(someFoodRequest)).thenReturn(someOrderItem);
        when(customerDAO.findCartByCustomerId(someId)).thenReturn(Optional.empty());
        when(customerDAO.addCart(someId)).thenReturn(someCart);
        doNothing().when(cartService).addItemToCart(someCart, someOrderItem);

        // When
        customerService.addFoodToCart(someId, someFoodRequest);

        // Then
        verify(requestMapper, times(1)).mapFoodRequestToOrderItem(someFoodRequest);
        verify(customerDAO, times(1)).findCartByCustomerId(someId);
        verify(customerDAO, times(1)).addCart(someId);
        verify(cartService, times(1)).addItemToCart(someCart, someOrderItem);
    }

    @Test
    void addFoodToCartWhenCartDoesExistWorksCorrectly() {
        // Given
        Integer someId = 32;
        FoodRequestDTO someFoodRequest = someFoodRequest();
        Cart someCart = someCart();
        OrderItem someOrderItem = OrderItemsUtils.someOrderItem1();
        when(requestMapper.mapFoodRequestToOrderItem(someFoodRequest)).thenReturn(someOrderItem);
        when(customerDAO.findCartByCustomerId(someId)).thenReturn(Optional.of(someCart));
        doNothing().when(cartService).addItemToCart(someCart, someOrderItem);

        // When
        customerService.addFoodToCart(someId, someFoodRequest);

        // Then
        verify(requestMapper, times(1)).mapFoodRequestToOrderItem(someFoodRequest);
        verify(customerDAO, times(1)).findCartByCustomerId(someId);
        verify(customerDAO, times(0)).addCart(someId);
        verify(cartService, times(1)).addItemToCart(someCart, someOrderItem);
    }


    @Test
    void getRestaurantsWithAddedFoodItemsWorksCorrectly() {
        // Given
        Integer someId = 65;
        Cart someCart = someCart();
        Menu someMenu1 = MenuUtils.someMenu();
        Menu someMenu2 = MenuUtils.someMenu().withMenuId(76);
        Menu someMenu3 = MenuUtils.someMenu().withMenuId(123);
        List<OrderDetailsDTO> someOrderDetailsDTOS = OrderUtils.someOrderDetailsList();
        when(customerDAO.findCartByCustomerId(someId)).thenReturn(Optional.of(someCart));
        when(orderItemService.findMenuByFood(any(Food.class)))
            .thenReturn(someMenu1, someMenu2, someMenu3);
        when(restaurantService.findRestaurantByMenu(any(Menu.class)))
            .thenReturn(someRestaurant1(), someRestaurant2(), someRestaurant3());
        when(orderRequestService.prepareOrderRequests(any(Cart.class), anySet()))
            .thenReturn(someOrderDetailsDTOS);

        // When
        List<OrderDetailsDTO> result = customerService.getRestaurantsWithAddedFoodItems(someId);

        // Then
        assertEquals(someOrderDetailsDTOS.size(), result.size());
        verify(orderItemService, times(someCart.getOrderItems().size())).findMenuByFood(any(Food.class));
        verify(restaurantService, times(someCart.getOrderItems().size())).findRestaurantByMenu(any(Menu.class));
        verify(orderRequestService, times(1)).prepareOrderRequests(any(Cart.class), anySet());
    }

    @Test
    void getRestaurantsWithAddedFoodItemsThrowsCorrectly() {
        // Given
        Integer someId = 65;
        String expectedMessage = "You don't have a cart. Add some food from the menu first!";
        when(customerDAO.findCartByCustomerId(someId)).thenReturn(Optional.empty());

        // When
        Throwable exception = assertThrows(NotFoundException.class, () -> customerService.getRestaurantsWithAddedFoodItems(someId));
        // Then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void findCancelableOrdersWorksCorrectly() {
        // Given
        Integer someId = 7;
        List<Order> someOrders = someOrders();
        when(orderService.findCancelableOrders(someId)).thenReturn(someOrders);
        OrderDTO orderDTO1 = someOrderDTO1();
        OrderDTO orderDTO2 = someOrderDTO2();
        OrdersDTO expected = OrdersDTO.builder().orders(List.of(orderDTO1,orderDTO2)).build();
        when(orderMapper.mapToDTO(any(Order.class))).thenReturn(orderDTO1, orderDTO2);
        // When
        OrdersDTO result = customerService.findCancelableOrders(someId);

        // Then
        assertEquals(expected,result);
    }

    @Test
    void payForBillWorksCorrectly() {
        // Given
        String someBillNumber = UUID.randomUUID().toString();
        doNothing().when(billService).payForBill(someBillNumber);
        // When
        customerService.payForBill(someBillNumber);

        // Then
        verify(billService,times(1)).payForBill(someBillNumber);
    }
    @Test
    void findCustomerRealizedOrdersWorksCorrectly() {
        // Given
        Order someOrder1 = someOrder1();
        Order someOrder2 = someOrder2();
        OrderDTO someOrderDTO = someOrderDTO1();
        Page<Order> somePaginatedOrders = new PageImpl<>(List.of(someOrder1, someOrder2));
        when(orderService.getPaginatedRealizedCustomerOrders(1, 7, 10, "asc", "orderNumber","completedAt"))
            .thenReturn(somePaginatedOrders);
        when(orderMapper.mapToDTO(any(Order.class))).thenReturn(someOrderDTO);

        // When
        Page<OrderDTO> result = customerService.findCustomerRealizedOrders(1, 7, 10, "asc", "orderNumber","completedAt");

        // Then
        assertEquals(somePaginatedOrders.getSize(),result.getSize());
        verify(orderMapper, times(somePaginatedOrders.getSize())).mapToDTO(any(Order.class));
        verify(orderService, times(1)).getPaginatedRealizedCustomerOrders(anyInt(), anyInt(), anyInt(), anyString(), any(String[].class));
    }

    @Test
    void findAllCustomersWorksCorrectly() {
        // Given

        Pageable pageable = PageRequest.of(3, 7);
        Page<Customer> somePaginatedCustomers = new PageImpl<>(List.of(someCustomer(), someCustomer2()));
        when(pageableService.preparePageable(anyInt(), anyInt(), anyString(), anyString())).thenReturn(pageable);
        when(customerDAO.findAllCustomers(any(Pageable.class))).thenReturn(somePaginatedCustomers);
        when(customerMapper.mapToDTO(any(Customer.class))).thenReturn(someCustomerDTO(), someCustomerDTO2());
        // When

        Page<CustomerDTO> result = customerService.findAllCustomers("name", "asc", 23, 2);

        // Then
        verify(customerMapper, times(somePaginatedCustomers.getSize())).mapToDTO(any(Customer.class));
        assertEquals(somePaginatedCustomers.getSize(),result.getSize());

    }
}