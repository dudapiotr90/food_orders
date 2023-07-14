package pl.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.database.entities.OrderEntity;
import pl.dudis.foodorders.infrastructure.database.entities.OwnerEntity;

@Repository
public interface OwnerJpaRepository extends JpaRepository<OwnerEntity,Integer> {
}
