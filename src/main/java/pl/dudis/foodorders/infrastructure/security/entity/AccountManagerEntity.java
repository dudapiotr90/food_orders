package pl.dudis.foodorders.infrastructure.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account_manager")
public class AccountManagerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_manager_id")
    private Integer accountManagerId;

    @Column(name="api_role_id")
    private Integer apiRoleId;

    @Column(name="account_id")
    private Integer accountId;
}
