package pl.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.database.entities.LocalTypeEntity;
import pl.dudis.foodorders.infrastructure.database.entities.MenuEntity;

@Repository
public interface MenuJpaRepository extends JpaRepository<MenuEntity,Integer> {
}