package com.dudis.foodorders.infrastructure.security.repository;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.infrastructure.security.repository.jpa.ConfirmationTokenJpaRepository;
import com.dudis.foodorders.services.dao.ConfirmationTokenDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ConfirmationTokenRepository implements ConfirmationTokenDAO {

    private final ConfirmationTokenJpaRepository confirmationTokenJpaRepository;

    @Override
    public String save(ConfirmationToken confirmationToken) {
        confirmationTokenJpaRepository.saveAndFlush(confirmationToken);
        return confirmationToken.getToken();
    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenJpaRepository.findByToken(token);
    }

    @Override
    public void deleteTokensByAccount(List<Integer> ids) {
        confirmationTokenJpaRepository.deleteAllByAccountIds(ids);
    }

    @Override
    public void setConfirmedAt(String token, OffsetDateTime confirmedAt) {
        confirmationTokenJpaRepository.updateConfirmedAt(token, confirmedAt);
    }


}
