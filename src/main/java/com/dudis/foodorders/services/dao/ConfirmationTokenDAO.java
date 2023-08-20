package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ConfirmationTokenDAO {
    String save(ConfirmationToken token);

    void setConfirmedAt(String token, OffsetDateTime confirmedAt);

    Optional<ConfirmationToken> getToken(String token);

    void deleteTokensByAccount(List<Integer> ids);
}
