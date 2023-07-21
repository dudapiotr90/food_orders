package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.domain.Local;

import java.util.List;

public interface LocalDAO {
    List<Local> findLocalsWhereOwnerId(Integer accountId);
}
