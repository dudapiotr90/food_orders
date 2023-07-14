package pl.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.database.entities.FoodTypeEntity;
import pl.dudis.foodorders.infrastructure.database.entities.LocalEntity;

@Repository
public interface LocalJpaRepository extends JpaRepository<LocalEntity,Integer> {
}
