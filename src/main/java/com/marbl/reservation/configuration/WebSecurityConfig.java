package com.marbl.reservation.configuration;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.cors(httpSecurityCorsConfigurer -> {
        }).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> {
            request.requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll();
            request.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll();
            request.requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll();
            request.requestMatchers(new AntPathRequestMatcher("/api/v1/registration")).permitAll();
            request.requestMatchers(new AntPathRequestMatcher("/api/v1/registration/verify")).permitAll();
            request.requestMatchers(new AntPathRequestMatcher("/api/v1/registration/verify/resend")).permitAll();
            request.requestMatchers(new AntPathRequestMatcher("/api/v1/passwords")).permitAll();
        });


        return http.build();
    }

}
