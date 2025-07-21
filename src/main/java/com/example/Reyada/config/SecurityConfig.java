package com.example.Reyada.config;

import com.example.Reyada.authentication.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable CSRF for simplicity, not recommended for production
                .authorizeHttpRequests()
                .requestMatchers(
                        "/",
                        "/auth.html",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/d/**",
                        "/auth/register",
                        "/auth/login",
                        "/sales_order.html",
                        "/deal.html?id=17"

                ).permitAll()
                // everything else still requires auth
                .anyRequest().authenticated()
                .and().httpBasic().and()
                .formLogin().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()) // always define a password encoder!
                .and()
                .build();
    }
}
