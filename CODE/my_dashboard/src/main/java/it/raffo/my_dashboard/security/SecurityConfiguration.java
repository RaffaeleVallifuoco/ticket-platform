package it.raffo.my_dashboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    // @Bean
    // SecurityFilterChain filterChain(HttpSecurity http)
    // throws Exception {
    // http.authorizeHttpRequests()
    // .requestMatchers("/home").permitAll()
    // .requestMatchers("/ticket/admin/**").hasAuthority("ADMIN")
    // .requestMatchers("/ticket/user/**").hasAuthority("OPERATOR")
    // .requestMatchers("/operator/**").hasAnyAuthority("OPERATOR", "ADMIN")
    // .requestMatchers("/ticket/{id}/**").hasAnyAuthority("ADMIN", "OPERATOR")
    // .requestMatchers("/css/**", "/js/**", "/webjars/**", "img/**")
    // .permitAll()
    // .and()
    // .formLogin()
    // .and()
    // .logout()
    // .and().exceptionHandling();

    // return http.build();
    // }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(request -> request
                        // queste sono rotte accessibili a tutti
                        .requestMatchers("/login", "/home", "/resources/**", "/logout", "/api/**", "img/**", "/css/**")
                        .permitAll()
                        .requestMatchers("/ticket/admin/**").hasAuthority("ADMIN")
                        // .requestMatchers("/ticket/user/**").hasAuthority("OPERATOR")
                        // .requestMatchers("/operator/**").hasAnyAuthority("OPERATOR", "ADMIN")
                        // .requestMatchers("/ticket/{id}/**").hasAnyAuthority("ADMIN", "OPERATOR")
                        // questo vuol dire che TUTTE le altre devono essere sotto autenticazione
                        .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/login") // Pagina di login personalizzata
                        .loginProcessingUrl("/authentication") // URL di elaborazione del login
                        .defaultSuccessUrl("/home") // Reindirizza dopo il successo del login
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logout-success")
                        .permitAll()); // Imposta il logout di default

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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
