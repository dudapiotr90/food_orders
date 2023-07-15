package pl.dudis.foodorders.infrastructure.security;

import jakarta.persistence.*;
import lombok.*;

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
    @Enumerated(EnumType.STRING)
    private ApiRole role;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "role")
    private Set<AccountEntity> accounts;
}
