package com.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "localId")
@ToString(of = {"localId", "name", "type"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "local")
public class LocalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "local_id")
    private Integer localId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private LocalTypeEntity type;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MenuEntity menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="owner_id")
    private OwnerEntity owner;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "local")
    private Set<DeliveryEntity> deliveries;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "local")
    private Set<DeliveryAddressEntity> deliveryAddresses;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "local")
    private Set<OrderEntity> orders;
}
