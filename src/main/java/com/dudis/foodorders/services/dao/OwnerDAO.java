package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.api.dtos.OwnerDTO;
import com.dudis.foodorders.domain.Local;
import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

import java.util.List;
import java.util.Optional;

public interface OwnerDAO {
    ConfirmationToken registerOwner(Owner owner);

    Optional<Owner> findOwnerById(Integer accountId);
}
