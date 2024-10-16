package com.signal.global.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] swagger = {
        "/open-api/**",
        "/resources/**",
        "/error",
        "/swagger-resources/**",
        "/swagger-ui/index.html",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/v3/api-docs"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(
            auth -> auth.requestMatchers(swagger).permitAll()
                .requestMatchers("/api/chatGPT/**").permitAll()
                .anyRequest().permitAll()

        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource () {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowed =
            Arrays.asList(
                "http://localhost:8080",
                "http://localhost:3000"
            );
        configuration.setAllowedOrigins(allowed);
        configuration.setAllowedMethods(
            Arrays.asList("GET", "Post", "PUT", "Delete", "PATCH", "OPTIONS")
        );
        configuration.setAllowedHeaders(
            Arrays.asList(
                "Authorization", "Cache-Control", "Content-Type"
            )
        );
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization", "Cache-Control", "Content-Type"
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
