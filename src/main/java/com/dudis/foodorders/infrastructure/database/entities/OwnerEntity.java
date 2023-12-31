package com.dudis.foodorders.infrastructure.database.entities;

import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@With
@Getter
@Setter
@EqualsAndHashCode(of = "ownerId")
@ToString(of = {"ownerId", "name", "surname"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "owner")
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner")
    private Set<RestaurantEntity> restaurants;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "owner")
    private Set<BillEntity> bills;

}
