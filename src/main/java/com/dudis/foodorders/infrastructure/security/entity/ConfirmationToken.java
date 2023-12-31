package com.dudis.foodorders.infrastructure.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
@With
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "confirmation_token_id")
    private Integer confirmationTokenId;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @Column(name = "confirmed_at")
    private OffsetDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

}
