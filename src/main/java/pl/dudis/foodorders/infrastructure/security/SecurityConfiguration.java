package pl.dudis.foodorders.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
        PasswordEncoder passwordEncoder,
        UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
        HttpSecurity httpSecurity,
        AuthenticationProvider authenticationProvider
    ) throws Exception {
        return httpSecurity
            .getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authenticationProvider)
            .build();
    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = true)
    public SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable) // probably to dont have logout form
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/","/login","/register", "/error").permitAll()
                    .requestMatchers("/**").permitAll()
//                requests.anyRequest().authenticated();
            )
            .formLogin(formLogin -> formLogin.permitAll())
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .build();

    }

    @Bean
    @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
    public SecurityFilterChain securityDisabled(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requests -> requests
                .anyRequest()
                .permitAll()
            )
            .build();
    }


}
