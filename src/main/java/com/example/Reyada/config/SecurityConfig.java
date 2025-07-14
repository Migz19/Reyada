package com.example.Reyada.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                // allow your UI pages and static assets
                .requestMatchers(
                        "/",
                        "/auth.html",      // your combined login/signup UI
                        "/profile.html",   // <— add this
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/crm/**",
                        "/contacts.html",    // <— add this
                        // allow the AJAX endpoints
                        "/auth/register",
                        "/auth/login",
                        "/contacts_dashboard.html"
                ).permitAll()
                // everything else still requires auth
                .anyRequest().authenticated()
                .and()
                .formLogin().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
