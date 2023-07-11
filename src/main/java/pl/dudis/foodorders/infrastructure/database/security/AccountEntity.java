package pl.dudis.foodorders.infrastructure.database.security;

import jakarta.persistence.*;
import lombok.*;
import pl.dudis.foodorders.infrastructure.database.entities.AddressEntity;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "accountId")
@ToString(of = {"accountId", "login"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer accountId;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "creation_date")
    private OffsetDateTime creationDate;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "account_manager",
        inverseJoinColumns = @JoinColumn(name="api_role_id"),
        joinColumns = @JoinColumn(name="account_id")
    )
    private Set<ApiRoleEntity> roles;


}
