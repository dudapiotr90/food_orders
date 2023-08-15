package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.api.mappers.*;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.CustomerDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;
    private final AccountService accountService;
    private final BillService billService;
    private final DeliveryAddressService deliveryAddressService;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final RestaurantService restaurantService;
    private final OrderRequestService orderRequestService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;
    private final RestaurantMapper restaurantMapper;
    private final RequestMapper requestMapper;
    private final PageableService pageableService;

    @Transactional
    public ConfirmationToken registerCustomer(RegistrationRequest request) {
        Account customerAccount = accountService.buildAccount(request);
        Customer customer = buildCustomer(customerAccount, request);
        return customerDAO.registerCustomer(customer);
    }

    public CustomerDTO findCustomerById(Integer id) {
        Optional<Customer> customer = customerDAO.findCustomerById(id);
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer doesn't exists");
        }
        return customerMapper.mapToDTO(customer.get());
    }

    public CustomerDTO findCustomerByAccountId(Integer accountId) {
        Optional<Customer> customer = customerDAO.findCustomerByAccountId(accountId);
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer doesn't exists");
        }
        return customerMapper.mapToDTO(customer.get());
    }

    public List<RestaurantForCustomerDTO> findRestaurantWithCustomerAddress(Integer accountId) {
        Account customerAccount = accountService.findCustomerAccount(accountId);
        Address address = customerAccount.getAddress();
        return deliveryAddressService.findRestaurantsWithAddress(address).stream()
            .map(restaurantMapper::mapToDTOForCustomer)
            .toList();
    }

    @Transactional
    public void addFoodToCart(Integer customerId, FoodRequestDTO foodToAdd) {
        OrderItem itemToAdd = requestMapper.mapFoodRequestToOrderItem(foodToAdd);
        Optional<Cart> customerCart = customerDAO.findCartByCustomerId(customerId);
        if (customerCart.isEmpty()) {
            Cart cart = customerDAO.addCart(customerId);
            cartService.addItemToCart(cart, itemToAdd);
        } else {
            cartService.addItemToCart(customerCart.get(), itemToAdd);
        }
    }

    private Customer buildCustomer(Account customerAccount, RegistrationRequest request) {
        return Customer.builder()
            .name(request.getUserName())
            .surname(request.getUserSurname())
            .account(customerAccount)
            .build();
    }

    public List<OrderDetailsDTO> getRestaurantsWithAddedFoodItems(Integer customerId) {
        Cart cart = findCartByCustomerId(customerId);
        Set<Restaurant> restaurants = cart.getOrderItems().stream()
            .map(o -> orderItemService.findMenuByFood(o.getFood()))
            .map(restaurantService::findRestaurantByMenu)
            .collect(Collectors.toSet());

        return orderRequestService.prepareOrderRequests(cart, restaurants);
    }

    public Cart findCartByCustomerId(Integer customerId) {
        return customerDAO.findCartByCustomerId(customerId)
            .orElseThrow(() -> new NotFoundException("You don't have a cart. Add some food from the menu first!"));
    }

    public OrdersDTO findCancelableOrders(Integer customerId) {
        return OrdersDTO.builder()
            .orders(orderService.findCancelableOrders(customerId).stream()
                .map(orderMapper::mapToDTO)
                .toList())
            .build();
    }

    @Transactional
    public void payForBill(String billNumber) {
        billService.payForBill(billNumber);
    }

    @Transactional
    public Page<OrderDTO> findCustomerRealizedOrders(
        Integer customerId,
        Integer pageNumber,
        int pageSize,
        String sortHow,
        String... sortBy
    ) {
        return orderService.getPaginatedRealizedCustomerOrders(customerId, pageNumber, pageSize, sortHow, sortBy)
            .map(orderMapper::mapToDTO);
    }

    public Page<CustomerDTO> findAllCustomers(String sortBy, String sortHow, Integer pageSize, Integer pageNumber) {
        Pageable pageable = pageableService.preparePageable(pageNumber, pageSize, sortHow, sortBy);
        return customerDAO.findPagedCustomers(pageable)
            .map(customerMapper::mapToDTO);
    }
}
