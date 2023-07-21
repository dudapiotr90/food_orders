package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Local;
import com.dudis.foodorders.services.dao.LocalDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocalService {

    private final LocalDAO localDAO;
    public List<Local> findOwnersLocals(Integer accountId) {
        return localDAO.findLocalsWhereOwnerId(accountId);
    }
}
