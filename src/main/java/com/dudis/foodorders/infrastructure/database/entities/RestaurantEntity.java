package com.dudis.foodorders.infrastructure.database.entities;

import com.dudis.foodorders.domain.LocalType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "restaurantId")
@ToString(of = {"restaurantId", "name", "type"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
//    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private LocalType type;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "menu")
    private MenuEntity menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="owner_id")
    private OwnerEntity owner;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "restaurant")
    private Set<DeliveryEntity> deliveries;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "restaurant")
    private Set<DeliveryAddressEntity> deliveryAddresses;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "restaurant")
    private Set<OrderEntity> orders;
}