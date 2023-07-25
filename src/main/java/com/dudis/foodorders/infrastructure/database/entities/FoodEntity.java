package com.dudis.foodorders.infrastructure.database.entities;

import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

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

    @Column(name="food_type")
    private String foodType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private MenuEntity menu;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "food_image_id")
    private FoodImageEntity foodImage;

}
