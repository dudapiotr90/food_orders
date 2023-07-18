package com.dudis.foodorders.infrastructure.security.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
//@Transactional(readOnly = true)
public interface ConfirmationTokenJpaRepository extends JpaRepository<ConfirmationToken, Integer> {

    Optional<ConfirmationToken> findByToken(String token);

    @Modifying
    @Query("UPDATE ConfirmationToken ct " +
            "SET ct.confirmedAt = ?2 " +
            "WHERE ct.token = ?1")
    void updateConfirmedAt(String token, OffsetDateTime confirmedAt);
}
