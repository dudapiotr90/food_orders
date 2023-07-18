package com.dudis.foodorders.infrastructure.security.repository;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ConfirmationTokenJpaRepository;
import com.dudis.foodorders.services.dao.ConfirmationTokenDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ConfirmationTokenRepository implements ConfirmationTokenDAO {

    private final ConfirmationTokenJpaRepository confirmationTokenJpaRepository;

    public String save(ConfirmationToken confirmationToken) {
        confirmationTokenJpaRepository.saveAndFlush(confirmationToken);
        return confirmationToken.getToken();
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenJpaRepository.findByToken(token);
    }

    public void setConfirmedAt(String token, OffsetDateTime confirmedAt) {
        confirmationTokenJpaRepository.updateConfirmedAt(token, confirmedAt);
    }


}
