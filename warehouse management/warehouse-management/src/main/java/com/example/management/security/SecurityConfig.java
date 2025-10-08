package com.example.management.security;

import com.example.management.entities.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("unused")
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // disable CSRF
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/addAdmin").hasRole("ADMIN")
                        .requestMatchers("/auth/addSupplier").hasRole("ADMIN")
                        .requestMatchers("/auth/signup").permitAll()
                        .requestMatchers("/auth/login").permitAll()

                        .requestMatchers("/inventory/**").hasAnyRole("SUPPLIER", "ADMIN")

                        .requestMatchers("/order/placeOrder").hasRole("USER")
                        .requestMatchers("/order/cancelOrder/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/order/approveOrder/**").hasRole("ADMIN")
                        .requestMatchers("/order/dispatchOrder/**").hasRole("SUPPLIER")
                        .requestMatchers("/order/completeOrder").hasRole("USER")

                        .requestMatchers("/warehouse/addWarehouse").hasRole("ADMIN")
                        .requestMatchers("/warehouse/deleteWarehouse/**").hasRole("ADMIN")
                        .requestMatchers("/warehouse/getWarehouse/**").hasAnyRole("ADMIN","SUPPLIER")

                        .requestMatchers("/product/addProduct").hasRole("ADMIN")
                        .requestMatchers("/product/**").permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // enables HTTP Basic authentication

        return http.build();
    }
}
