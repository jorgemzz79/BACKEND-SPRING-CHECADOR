package com.backend.APIRest.utils;

import com.backend.APIRest.repository.checador.UsuarioRepository;
import com.backend.APIRest.service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)

    {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

            if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
                try {
                    filterChain.doFilter(request, response);
                } catch (IOException e) {
                    System.out.println("GALINDOOOOO:" + e.getMessage());
                    throw new Excepcion(e.getMessage());

                    //throw new RuntimeException(e);

                } catch (ServletException e) {
                    System.out.println("GALINDOOOOO servlet"+e.getMessage());
                   // throw new RuntimeException(e);
                }
                return;
            }
            if ("/usuarios/login".equals(request.getServletPath())) {
                // No hacer nada y continuar con la cadena de filtros
                try {
                    filterChain.doFilter(request, response);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    throw new Excepcion(e.getMessage());
                   // throw new RuntimeException(e);
                } catch (ServletException e) {
                    System.out.println("servlet"+e.getMessage());
                    //throw new RuntimeException(e);
                }
                return;
            }
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUserName(jwt);
            if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UserDetails userDetails = new UserDetailsService()
                {
                    @Override
                    public UserDetails loadUserByUsername(String username)
                    {
                        return usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
                    }
                }.loadUserByUsername(userEmail);

                if (jwtService.isTokenAboutToExpire(jwt))
                {
                    // Si el token está a punto de expirar, podrías renovarlo aquí
                    // Por ejemplo, generando un nuevo token y actualizando la respuesta HTTP
                    String newToken = jwtService.generateToken(userDetails);
                    response.setHeader("Authorization", "Bearer " + newToken);
                }

                if (jwtService.isTokenValid(jwt, userDetails))
                {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    context.setAuthentication(authToken);
                    SecurityContextHolder.setContext(context);
                }
            }
        try {
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new Excepcion(e.getMessage());
           // throw new RuntimeException(e);
        } catch (ServletException e) {
            System.out.println("servlet"+e.getMessage());
            throw new Excepcion(e.getMessage());
            //throw new RuntimeException(e);
        }
    }


}