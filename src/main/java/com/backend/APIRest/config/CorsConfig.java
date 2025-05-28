package com.backend.APIRest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200"); // Permite el acceso desde localhost
        configuration.addAllowedOrigin("http://192.168.1.156"); // Permite el acceso desde la IP 192.168.1.100
        configuration.addAllowedOrigin("http://192.168.1.160"); // Permite el acceso desde la IP 192.168.1.161
        configuration.addAllowedMethod("*"); // Define los métodos HTTP permitidos (GET, POST, etc.)
        configuration.addAllowedHeader("*"); // Define los encabezados permitidos

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica esta configuración a todas las rutas

        return source;
    }
}