package com.dudis.foodorders.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountJpaRepository;

@Service
@AllArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return accountJpaRepository.findByLogin(login)
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format("Account with login: [%s] not found",login)
            ));
    }
}
