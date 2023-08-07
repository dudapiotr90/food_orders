package com.dudis.foodorders.infrastructure.database.entities;

import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "city", "postalCode", "street","residenceNumber"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street")
    private String street;

    @Column(name = "residence_number")
    private String residenceNumber;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "address")
    private AccountEntity account;
}
