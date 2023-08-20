package com.dudis.foodorders.infrastructure.database.entities;

import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "developerId")
@ToString(of = {"developerId", "name", "surname"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "developer")
public class DeveloperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "developer_id")
    Integer developerId;

    @Column(name = "name")
    String name;

    @Column(name = "surname")
    String surname;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    AccountEntity account;
}
