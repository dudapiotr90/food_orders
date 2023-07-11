package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "food_api_role")
public class FoodApiRoleEntity {
}
