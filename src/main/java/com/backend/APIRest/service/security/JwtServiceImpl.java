package com.backend.APIRest.service.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);
    @Value("${token.expirationTime}")
    private long expirationTime;
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);

    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers)
    {
        final Claims claims = extractAllClaims(token);

            return claimsResolvers.apply(claims);
    }
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Define el tiempo de expiración del token
        //long expirationTime = 1000 * 60 * 60 * 12; // 12 Horas  en milisegundos
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities()) // Agregar roles como una claim personalizada
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenAboutToExpire(String token) {
        // Obtiene la fecha de expiración del token
        Date expirationDate = extractExpiration(token);
        // Define un tiempo antes de la expiración en milisegundos (por ejemplo, 5 minutos)
        long expirationThreshold = 1000 * 60 * 15; // 15 minutos en milisegundos
        // Calcula la fecha actual
        Date currentDate = new Date();
        // Comprueba si el token está a punto de expirar
        return expirationDate != null && expirationDate.getTime() - currentDate.getTime() <= expirationThreshold;
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws  MalformedJwtException
    {
//            Claims claims = Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
//            return claims;

        try {
            Claims claims = Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
            return claims;
        } catch (MalformedJwtException e) {
            // Manejar la excepción. Puedes loggear un mensaje de error, notificar al usuario, etc.
            // Por ejemplo, loggear el mensaje de error:
            log.error("Token JWT corrupto o con formato incorrecto: "+ e.getMessage());

            // Puedes lanzar una excepción personalizada si es necesario.
            // throw new MiExcepcionPersonalizada("Token JWT corrupto o con formato incorrecto", e);

            // Puedes devolver un valor predeterminado o nulo, según tu lógica.
            return null;
        }

    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}