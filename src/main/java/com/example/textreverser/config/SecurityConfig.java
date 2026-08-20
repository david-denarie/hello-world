package com.example.textreverser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration de sécurité :
 * - CORS restreint aux origines autorisées
 * - Headers de sécurité HTTP (X-Content-Type-Options, X-Frame-Options, HSTS, etc.)
 * - CSRF désactivé car API REST stateless
 * - Pas d'authentification requise (API publique)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CORS : utilise la configuration définie ci-dessous
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // CSRF désactivé : API REST stateless sans cookies de session
            .csrf(csrf -> csrf.disable())

            // Session stateless (pas de session côté serveur)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Toutes les requêtes sont autorisées (API publique)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

            // Headers de sécurité (Spring Security les ajoute par défaut) :
            // - X-Content-Type-Options: nosniff
            // - X-Frame-Options: DENY
            // - X-XSS-Protection: 0 (désactivé car remplacé par CSP)
            // - Cache-Control: no-cache, no-store
            // On ajoute explicitement HSTS et CSP
            .headers(headers -> headers
                .httpStrictTransportSecurity(hsts -> hsts
                    .includeSubDomains(true)
                    .maxAgeInSeconds(31536000)
                )
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'; script-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self'")
                )
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Origines autorisées — à adapter selon l'environnement
        configuration.setAllowedOrigins(List.of(
            "http://localhost:4200",   // Angular dev server
            "https://localhost:4200"
        ));

        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Content-Type", "Accept"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
