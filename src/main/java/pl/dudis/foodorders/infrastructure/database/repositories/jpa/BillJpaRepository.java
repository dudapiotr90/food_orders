package pl.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.database.entities.AddressEntity;
import pl.dudis.foodorders.infrastructure.database.entities.BillEntity;

@Repository
public interface BillJpaRepository extends JpaRepository<BillEntity,Integer> {
}
