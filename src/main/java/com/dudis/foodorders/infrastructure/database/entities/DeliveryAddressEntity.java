package com.dudis.foodorders.infrastructure.database.entities;

import com.dudis.foodorders.infrastructure.database.entities.utility.RestaurantFromNamedNativeQuery;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NamedNativeQuery;

@Getter
@Setter
@EqualsAndHashCode(of = "deliveryAddressId")
@ToString(of = {"deliveryAddressId", "city", "postalCode", "street"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "delivery_address")
//@NamedNativeQuery(
//    name = "DeliveryAddressEntity.findRestaurantsIdWithAddress",
//    query = """
//        SELECT res.restaurant_id, res.name, res.description, res.local_type, res.menu_id
//        FROM delivery_address da
//        JOIN restaurant res ON da.restaurant_id = res.restaurant_id
//        WHERE da.city = :city
//        AND da.postal_code = :postalCode
//        AND da.street LIKE :street
//         """,
//    resultClass = Object[].class
//)
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
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;
}
