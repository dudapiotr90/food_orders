package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.BillDTO;
import com.dudis.foodorders.api.dtos.DeliveryDTO;
import com.dudis.foodorders.api.dtos.LocalDTO;
import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.api.mappers.BillMapper;
import com.dudis.foodorders.api.mappers.DeliveryMapper;
import com.dudis.foodorders.api.mappers.LocalMapper;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.database.mappers.OwnerMapper;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.OwnerDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.dudis.foodorders.infrastructure.security.RegistrationRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;
    private final AccountService accountService;
    private final LocalService localService;
    private final DeliveryService deliveryService;
    private final BillService billService;

    private final OwnerMapper ownerMapper;
    private final LocalMapper localMapper;
    private final DeliveryMapper deliveryMapper;
    private final BillMapper billMapper;

    public ConfirmationToken registerOwner(RegistrationRequest request) {
        Account ownerAccount = accountService.buildAccount(request);
        Owner owner = buildOwner(ownerAccount, request);
        return ownerDAO.registerOwner(owner);
    }

    private Owner buildOwner(Account ownerAccount, RegistrationRequest request) {
        return Owner.builder()
            .name(request.getUserName())
            .surname(request.getUserSurname())
            .account(ownerAccount)
            .build();
    }

    public Account findOwnerByLogin(String login) {
        return accountService.findByLogin(login);
    }

    public List<LocalDTO> findAllOwnerLocals(Integer accountId) {
        return localService.findOwnersLocals(accountId).stream()
            .map(localMapper::mapToDTO)
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

    public OwnerDTO findOwnerById(Integer accountId) {
        Optional<Owner> owner = ownerDAO.findOwnerById(accountId);
        if (owner.isEmpty()) {
            throw new NotFoundException("Owner doesn't exists");
        }
        return ownerMapper.mapToDTO(owner.get());
    }

}
