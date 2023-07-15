package pl.dudis.foodorders.infrastructure.database.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dudis.foodorders.infrastructure.database.entities.AddressEntity;

@Repository
public interface AddressJpaRepository extends JpaRepository<AddressEntity,Integer> {

}
