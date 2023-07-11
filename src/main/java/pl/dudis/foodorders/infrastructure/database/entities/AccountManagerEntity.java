package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "mechanicId")
@ToString(of = {"mechanicId", "name", "surname", "pesel"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account_manager")
public class AccountManagerEntity {
}
