package com.dudis.foodorders.services;

import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.domain.Food;
import com.dudis.foodorders.domain.Menu;
import com.dudis.foodorders.services.dao.MenuDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuService{

    private final MenuDAO menuDAO;
    private final FoodService foodService;
    private final MenuMapper menuMapper;


}
