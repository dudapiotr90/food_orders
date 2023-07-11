package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;
import pl.dudis.foodorders.infrastructure.database.security.AccountEntity;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "deliveryServiceId")
@ToString(of = {"deliveryServiceId", "deliveryNumber", "price", "address"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "delivery_service")
public class DeliveryServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_service_id")
    private Integer deliveryServiceId;

    @Column(name = "delivery_number",unique = true)
    private String deliveryNumber;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id")
    private LocalEntity local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;
}
