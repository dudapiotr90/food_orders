package pl.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.database.entities.FoodEntity;
import pl.dudis.foodorders.infrastructure.database.entities.FoodTypeEntity;

@Repository
public interface FoodTypeJpaRepository extends JpaRepository<FoodTypeEntity,Integer> {
}
