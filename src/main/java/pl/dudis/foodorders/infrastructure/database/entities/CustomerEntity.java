package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;
import pl.dudis.foodorders.infrastructure.security.AccountEntity;

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
    @PrimaryKeyJoinColumn
    private AccountEntity account;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "customer")
    private Set<OrderEntity> orders;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "customer")
    private Set<BillEntity> bills;
}
