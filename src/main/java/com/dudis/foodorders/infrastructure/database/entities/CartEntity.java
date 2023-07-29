package com.dudis.foodorders.infrastructure.database.entities;

import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "cartId")
@ToString(of = {"cartId"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "cart")
    private CustomerEntity customer;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "cart")
    private Set<OrderDetailEntity> orderDetails;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart")
    private List<OrderItemEntity> orderItems;


}
