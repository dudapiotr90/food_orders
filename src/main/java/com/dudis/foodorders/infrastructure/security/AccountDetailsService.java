package com.dudis.foodorders.infrastructure.security;

import com.dudis.foodorders.infrastructure.security.entity.AccountEntity;
import com.dudis.foodorders.infrastructure.security.entity.ApiRoleEntity;
import com.dudis.foodorders.infrastructure.security.repository.jpa.AccountJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountDetailsService implements UserDetailsService {

    private final AccountJpaRepository accountJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AccountEntity account = accountJpaRepository.findByLogin(login)
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format("Account with login: [%s] not found", login)
            ));
        Collection<? extends GrantedAuthority> authority = account.getAuthorities();
        return new User(
            account.getUsername(),
            account.getPassword(),
            account.isEnabled(),
            account.isAccountNonExpired(),
            account.isCredentialsNonExpired(),
            account.isAccountNonLocked(),
            authority);
    }

//    private UserDetails createAccountAccess(AccountEntity account, Set<SimpleGrantedAuthority> authorities) {
//        return new User(account.getLogin(),account.getPassword(),account.getEnabled(),true,)
//    }
//
//    private Set<SimpleGrantedAuthority> findAccountAuthority(Set<ApiRoleEntity> roles) {
//        return roles.stream()
//            .map(role->new SimpleGrantedAuthority(role.getRole()))
//            .collect(Collectors.toSet());
//    }

}
