package com.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "orderId")
@ToString(of = {"orderId", "orderNumber", "receivedDateTime", "completedDateTime","customerComment"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_number",unique = true)
    private String orderNumber;

    @Column(name = "received_date_time")
    private OffsetDateTime receivedDateTime;

    @Column(name = "completed_date_time")
    private OffsetDateTime completedDateTime;

    @Column(name = "customer_comment")
    private String customerComment;

    @Column(name = "realized")
    private Boolean realized;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "order")
    private Set<OrderItemEntity> orderItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
