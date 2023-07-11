package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;
import pl.dudis.foodorders.infrastructure.database.security.AccountEntity;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "foodId")
@ToString(of = {"name", "foodType"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "food")
public class FoodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Integer foodId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_type_id")
    private FoodTypeEntity foodType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

}
