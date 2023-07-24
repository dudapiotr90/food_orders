package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.api.dtos.DeliveryDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.dtos.RestaurantDTO;
import com.dudis.foodorders.api.mappers.BillMapper;
import com.dudis.foodorders.api.mappers.DeliveryMapper;
import com.dudis.foodorders.api.mappers.RestaurantMapper;
import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.database.mappers.OwnerMapper;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.OwnerDAO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;
    private final AccountService accountService;
    private final RestaurantService restaurantService;
    private final DeliveryService deliveryService;
    private final BillService billService;

    private final OwnerMapper ownerMapper;
    private final RestaurantMapper restaurantMapper;
    private final DeliveryMapper deliveryMapper;
    private final BillMapper billMapper;

    public ConfirmationToken registerOwner(RegistrationRequest request) {
        Account ownerAccount = accountService.buildAccount(request);
        Owner owner = buildOwner(ownerAccount, request);
        return ownerDAO.registerOwner(owner);
    }

    public Account findOwnerByLogin(String login) {
        return accountService.findByLogin(login);
    }

    public List<RestaurantDTO> findAllOwnerRestaurants(Integer ownerId) {
        return restaurantService.findOwnersLocals(ownerId).stream()
            .map(restaurantMapper::mapToDTO)
            .toList();
    }

    public List<DeliveryDTO> findPendingDeliveries(Integer accountId) {
        return deliveryService.findPendingDeliveries(accountId,true).stream()
            .map(deliveryMapper::mapToDTO)
            .toList();
    }

    public List<BillDTO> findPendingBills(Integer accountId) {
        return billService.findPendingBills(accountId,false).stream()
            .map(billMapper::mapToDTO)
            .toList();
    }

    public OwnerDTO findOwnerByAccountId(Integer accountId) {
        Optional<Owner> owner = ownerDAO.findOwnerByAccountId(accountId);
        if (owner.isEmpty()) {
            throw new NotFoundException("Owner doesn't exists");
        }
        return ownerMapper.mapToDTO(owner.get());
    }

    @Transactional
    public void addRestaurant(Integer ownerId, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantMapper.mapFromDTO(restaurantDTO);

        Optional<Owner> owner = ownerDAO.findOwnerById(ownerId);
        if (owner.isEmpty()) {
            throw new NotFoundException("Owner doesn't exists");
        }
        restaurantService.addLocal(restaurant.withOwner(owner.get()).withMenu(buildMenu(restaurantDTO)));
    }

    private Menu buildMenu(RestaurantDTO restaurantDTO) {
        return Menu.builder()
            .menuName(restaurantDTO.getName())
            .description(restaurantDTO.getDescription())
            .build();
    }


    private Owner buildOwner(Account ownerAccount, RegistrationRequest request) {
        return Owner.builder()
            .name(request.getUserName())
            .surname(request.getUserSurname())
            .account(ownerAccount)
            .build();
    }

    public OwnerDTO findOwnerById(Integer ownerId) {
        Optional<Owner> owner = ownerDAO.findOwnerById(ownerId);
        if (owner.isEmpty()) {
            throw new NotFoundException("Owner doesn't exists");
        }
        return ownerMapper.mapToDTO(owner.get());
    }
}
