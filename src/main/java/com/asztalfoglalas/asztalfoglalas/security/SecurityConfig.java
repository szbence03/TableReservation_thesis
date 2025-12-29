package com.asztalfoglalas.asztalfoglalas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config ->
                        config
                                .requestMatchers(
                                        "/",
                                        "/regisztracio**",
                                        "/bejelentkezes").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/bejelentkezes")
                                .loginProcessingUrl("/bejelentkezes-feldolgozas")
                                .defaultSuccessUrl("/", true)
                                .usernameParameter("email")
                                .passwordParameter("jelszo")
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/kijelentkezes")
                        .logoutSuccessUrl("/bejelentkezes?kijelentkezve")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                );

        return http.build();
    }


}
