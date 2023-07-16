package pl.dudis.foodorders.infrastructure.security.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.dudis.foodorders.infrastructure.security.Role;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "api_role")
public class ApiRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="api_role_id")
    private Integer apiRoleId;

    @Column(name = "role")
//    @Enumerated(EnumType.STRING)
    private String role;

//    @OneToMany(fetch = FetchType.LAZY,mappedBy = "role",cascade = CascadeType.PERSIST)
//    private Set<AccountEntity> accounts;
}