package dev.sezrr.examples.llmchatservice.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/v1/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/models", "/v1/models/{id}", "/v1/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/users", "/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/public/**", "/v1/users/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT,
                                "/public/**",
                                "/v1/users/{id}/verify",
                                "/v1/users/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/configuration/**",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/api-docs/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }
}
