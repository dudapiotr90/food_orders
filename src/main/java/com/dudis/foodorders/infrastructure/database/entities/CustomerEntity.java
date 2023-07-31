package com.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "customerId")
@ToString(of = {"customerId", "name", "surname"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer")
    private Set<BillEntity> bills;
}
