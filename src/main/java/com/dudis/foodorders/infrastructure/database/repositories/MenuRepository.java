package com.dudis.foodorders.infrastructure.database.repositories;

import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.infrastructure.database.mappers.MenuEntityMapper;
import com.dudis.foodorders.infrastructure.database.repositories.jpa.MenuJpaRepository;
import com.dudis.foodorders.services.dao.MenuDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MenuRepository implements MenuDAO {

    private final MenuJpaRepository menuJpaRepository;
    private final MenuEntityMapper menuEntityMapper;
}
