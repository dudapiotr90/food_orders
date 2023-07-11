package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;
import pl.dudis.foodorders.infrastructure.database.security.AccountEntity;

@Getter
@Setter
@EqualsAndHashCode(of = "deliveryAddressId")
@ToString(of = {"deliveryAddressId", "city", "postalCode", "street"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "delivery_address")
public class DeliveryAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_address_id")
    private Integer deliveryAddressId;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street")
    private String street;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id")
    private LocalEntity local;
}
