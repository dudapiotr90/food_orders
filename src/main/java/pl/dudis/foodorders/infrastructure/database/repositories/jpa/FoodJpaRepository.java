package pl.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.database.entities.DeliveryEntity;
import pl.dudis.foodorders.infrastructure.database.entities.FoodEntity;

@Repository
public interface FoodJpaRepository extends JpaRepository<FoodEntity,Integer> {
}
