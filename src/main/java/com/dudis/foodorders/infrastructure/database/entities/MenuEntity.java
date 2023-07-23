package com.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "menuId")
@ToString(of = {"menuId", "menuName"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menu")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "menu")
    private RestaurantEntity restaurant;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "menu")
    private Set<FoodEntity> foods;

}
