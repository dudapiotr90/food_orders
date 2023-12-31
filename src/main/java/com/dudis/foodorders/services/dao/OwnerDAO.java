package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OwnerDAO {
    ConfirmationToken registerOwner(Owner owner);

    Optional<Owner> findOwnerByAccountId(Integer accountId);

    Optional<Owner> findOwnerById(Integer ownerId);

    Page<Owner> findAllOwners(Pageable pageable);

    void deleteByAccountId(Integer accountId);
}
