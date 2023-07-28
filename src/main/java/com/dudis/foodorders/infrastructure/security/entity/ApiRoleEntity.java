package com.dudis.foodorders.infrastructure.security.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String role;

}