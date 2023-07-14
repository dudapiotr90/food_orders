package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;
import pl.dudis.foodorders.infrastructure.security.AccountEntity;

@Getter
@Setter
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "city", "postalCode", "address"})
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

    @Column(name = "address")
    private String address;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "address")
    private AccountEntity account;
}
