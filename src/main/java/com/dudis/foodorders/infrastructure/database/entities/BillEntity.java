package com.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "billId")
@ToString(of = {"billId", "billNumber", "dateTime"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bill")
public class BillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Integer billId;

    @Column(name = "bill_number",unique = true)
    private String billNumber;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name="payed")
    private Boolean payed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;


}
