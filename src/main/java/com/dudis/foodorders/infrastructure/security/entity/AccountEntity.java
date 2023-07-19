package com.dudis.foodorders.infrastructure.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.dudis.foodorders.infrastructure.database.entities.AddressEntity;
import com.dudis.foodorders.infrastructure.security.AuthorityException;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
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
    @Column(name="account_id")
    private Integer accountId;

    @Column(name = "login", unique = true)
    @Length(min = 4)
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

    @Column(name = "api_role_id")
    private Integer roleId;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "unlocked")
    @Builder.Default
    private Boolean unlocked = false;

    @Column(name = "enabled")
    @Builder.Default
    private Boolean enabled = false;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
        name = "account_manager",
        inverseJoinColumns = @JoinColumn(name="api_role_id"),
        joinColumns = @JoinColumn(name="account_id")
    )
    private Set<ApiRoleEntity> roles;

//    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinColumn(name = "api_role_id")
//    private ApiRoleEntity role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roles.stream()
            .map(ApiRoleEntity::getRole)
            .findAny().orElseThrow(() -> new AuthorityException(
                String.format("Account [%s] has no authority",this.login)
            )));
        return Collections.singleton(authority);
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !unlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
