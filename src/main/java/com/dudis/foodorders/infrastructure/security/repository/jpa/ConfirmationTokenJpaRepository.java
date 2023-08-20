package com.dudis.foodorders.infrastructure.security.repository.jpa;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
//@Transactional(readOnly = true)
public interface ConfirmationTokenJpaRepository extends JpaRepository<ConfirmationToken, Integer> {

    Optional<ConfirmationToken> findByToken(String token);

    @Modifying
    @Query("""
        UPDATE ConfirmationToken ct
        SET ct.confirmedAt = ?2
        WHERE ct.token = ?1
        """)
    void updateConfirmedAt(String token, OffsetDateTime confirmedAt);

    @Modifying
    @Query(value = """
        DELETE FROM confirmation_token ct
        WHERE ct.account_id IN (:ids)
        """,nativeQuery = true)
    void deleteAllByAccountIds(List<Integer> ids);
}
