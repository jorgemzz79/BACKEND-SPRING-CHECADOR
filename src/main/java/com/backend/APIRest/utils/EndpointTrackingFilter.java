package com.backend.APIRest.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@WebFilter
public class EndpointTrackingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtiene el endpoint actual desde la solicitud
        String endpoint = request.getRequestURI();

        // Almacena el endpoint en el ThreadLocal
        EndpointContextHolder.setEndpoint(endpoint);

        try {
            // Continúa con el procesamiento de la solicitud
            filterChain.doFilter(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Asegúrate de limpiar el ThreadLocal después de que la solicitud se haya procesado
            EndpointContextHolder.clearEndpoint();
        }
    }
}