package it.raffo.my_dashboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/home").permitAll()
                .requestMatchers("/ticket/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/ticket/user/**").hasAuthority("OPERATOR")
                .requestMatchers("/operator/**").hasAuthority("OPERATOR")
                .requestMatchers("/ticket/{id}/**").hasAnyAuthority("ADMIN", "OPERATOR")
                .requestMatchers("/css/**", "/js/**", "/webjars/**", "img/**")
                .permitAll()
                .and()
                .formLogin()
                .and()
                .logout()
                .and().exceptionHandling();

        return http.build();
    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
