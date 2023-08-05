package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.domain.Restaurant;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

import java.util.Optional;

public interface OwnerDAO {
    ConfirmationToken registerOwner(Owner owner);

    Optional<Owner> findOwnerByAccountId(Integer accountId);

    Optional<Owner> findOwnerById(Integer ownerId);

}
