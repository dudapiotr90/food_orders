package pl.dudis.foodorders.infrastructure.security;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.dudis.foodorders.infrastructure.database.entities.AddressEntity;

import java.time.OffsetDateTime;
import java.util.Collection;
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
public class AccountEntity implements UserDetails {

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

//    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(
//        name = "account_manager",
//        inverseJoinColumns = @JoinColumn(name="api_role_id"),
//        joinColumns = @JoinColumn(name="account_id")
//    )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_role_id")
    private ApiRoleEntity role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
