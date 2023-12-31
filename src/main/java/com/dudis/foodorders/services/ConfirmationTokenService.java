package com.dudis.foodorders.services;

import com.dudis.foodorders.domain.Account;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.domain.exception.RegistrationException;
import com.dudis.foodorders.infrastructure.security.entity.AccountEntityMapper;
import com.dudis.foodorders.infrastructure.security.entity.ConfirmationToken;
import com.dudis.foodorders.services.dao.ConfirmationTokenDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenDAO confirmationTokenDAO;
    private final AccountEntityMapper accountEntityMapper;
    private final AccountService accountService;

    public String saveConfirmationToken(ConfirmationToken token) {
        return confirmationTokenDAO.save(token);
    }

    public Account confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenDAO.getToken(token)
            .orElseThrow(() -> new NotFoundException("Token [%s] not found".formatted(token)));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new RegistrationException("Email already confirmed");
        }
        if (confirmationToken.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new RegistrationException("Token expired");
        }
        confirmationTokenDAO.setConfirmedAt(token, OffsetDateTime.now());
        accountService.enableAccount(confirmationToken.getAccountEntity().getAccountId());
        return accountEntityMapper.mapFromEntity(confirmationToken.getAccountEntity());
    }

    public void deleteTokensByAccount(List<Integer> ids) {
        confirmationTokenDAO.deleteTokensByAccount(ids);
    }
}
