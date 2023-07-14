package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;
import pl.dudis.foodorders.infrastructure.security.AccountEntity;

import java.util.Set;

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
    @PrimaryKeyJoinColumn
    private AccountEntity account;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "owner")
    private Set<LocalEntity> locals;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "owner")
    private Set<BillEntity> bills;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "owner")
    private Set<DeliveryEntity> deliveries;
}