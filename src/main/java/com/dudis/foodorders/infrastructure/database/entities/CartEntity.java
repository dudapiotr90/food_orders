package com.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "cartId")
@ToString(of = {"cartId"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name="customer_id")
    private Integer customerId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
    private Set<OrderItemEntity> orderItems;


}
