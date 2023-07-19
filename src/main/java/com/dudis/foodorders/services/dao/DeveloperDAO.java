package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Developer;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

public interface DeveloperDAO {
    ConfirmationToken registerDeveloper(Developer developer);
}
