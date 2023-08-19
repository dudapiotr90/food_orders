package com.dudis.foodorders.infrastructure.database.repositories.jpa;

import com.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import com.dudis.foodorders.infrastructure.database.entities.MenuEntity;
import com.dudis.foodorders.integration.configuration.PersistanceContainerTestConfiguration;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.dudis.foodorders.utils.FoodUtils.someFoodEntities;
import static com.dudis.foodorders.utils.FoodUtils.someFoodEntity2;
import static com.dudis.foodorders.utils.MenuUtils.someMenuEntity1;
import static com.dudis.foodorders.utils.MenuUtils.someMenuToPersist1;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(PersistanceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodJpaRepositoryTest {

    private FoodJpaRepository foodJpaRepository;
    private MenuJpaRepository menuJpaRepository;

    @BeforeEach
    void setup() {
        MenuEntity menuToPersist = someMenuToPersist1();
        MenuEntity menu = menuJpaRepository.save(menuToPersist);
        List<FoodEntity> foods = someFoodEntities();
        foods.forEach(foodEntity -> {
            foodEntity.setMenu(menu);
            foodEntity.setFoodId(null);
        });
        foodJpaRepository.saveAll(foods);
    }

    @Test
    void findPagedByMenuIdWorksCorrectly() {
        // Given
        List<MenuEntity> all = menuJpaRepository.findAll();
        Pageable pageable = PageRequest.of(1, 1, Sort.by("price").ascending());

        // When
        Page<FoodEntity> result1 = foodJpaRepository.findByMenuId(all.get(0).getMenuId(), pageable);
        Page<FoodEntity> result2 = foodJpaRepository.findByMenuId(18797, pageable);
        // Then
        assertThat(result1.getSize()).isEqualTo(1);
        assertThat(result1.getTotalElements()).isEqualTo(someFoodEntities().size());
        assertThat(result2).isEmpty();
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void findMenuByFoodIdWorksCorrectly() {
        // Given
        Optional<FoodEntity> food = foodJpaRepository.findAll().stream().findAny();
        assertThat(food).isNotEmpty();

        // When, Then
        MenuEntity result = foodJpaRepository.findMenuByFoodId(food.get().getFoodId());
        assertThat(result).isNotNull();
    }

    @Test
    void findByMenuIdWorksCorrectly() {
        // Given
        List<MenuEntity> all = menuJpaRepository.findAll();

        // When, Then
        List<FoodEntity> result1 = foodJpaRepository.findByMenuId(all.get(0).getMenuId());
        List<FoodEntity> result2 = foodJpaRepository.findByMenuId(18797);

        assertThat(result1).isNotNull();
        assertThat(result2).isEmpty();
        assertThat(result1.size()).isEqualTo(someFoodEntities().size());
    }

}