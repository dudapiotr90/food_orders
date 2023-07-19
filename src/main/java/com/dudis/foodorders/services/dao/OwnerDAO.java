package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Owner;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

public interface OwnerDAO {
    ConfirmationToken registerOwner(Owner owner);
}
