package com.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"deliveryAddressId","city","postalCode","street"})
@ToString(of = {"deliveryAddressId", "city", "postalCode", "street"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "delivery_address")
public class DeliveryAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_id")
    private Integer deliveryAddressId;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street")
    private String street;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}
