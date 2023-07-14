package pl.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.database.entities.BillEntity;
import pl.dudis.foodorders.infrastructure.database.entities.CustomerEntity;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity,Integer> {
}
