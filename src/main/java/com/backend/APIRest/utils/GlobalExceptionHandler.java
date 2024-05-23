package com.backend.APIRest.utils;
import com.backend.APIRest.controlller.UsuarioController;
import com.backend.APIRest.model.dto.response.respuesta.Contenido;
import com.backend.APIRest.model.dto.response.respuesta.Respuesta;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Excepcion.class)
    public ResponseEntity<Contenido<String>> handleException(Excepcion ex) {
        String mensaje = "Error al procesar la información";
        String detalles = ex.getMessage();
        return new Respuesta<>(HttpStatus.CONFLICT, null, mensaje, detalles);
    }

    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class, JwtException.class})
    public ResponseEntity<Contenido<String>> handleAuthenticationExceptions(Exception ex) {
        String mensaje = "Error de autenticación";
        String detalles = ex.getMessage();
        HttpStatus status = ex instanceof JwtException ? HttpStatus.FORBIDDEN : HttpStatus.UNAUTHORIZED;
        return new Respuesta<>(status, null, mensaje, detalles);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Contenido<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String mensaje = "Los datos introducidos no cumplen los requisitos establecidos";
        String detalles = obtenerDetallesDeValidacion(ex);
        return new Respuesta<>(HttpStatus.BAD_REQUEST, null, mensaje, detalles);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Contenido<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        String mensaje = "Error en los datos proporcionados";
        String detalles = ex.getMessage();
        return new Respuesta<>(HttpStatus.BAD_REQUEST, null, mensaje, detalles);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Contenido<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        String mensaje = "No se encontró el registro en la Base de Datos";
        String detalles = ex.getMessage();
        return new Respuesta<>(HttpStatus.UNAUTHORIZED, null, mensaje, detalles);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Contenido<String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        String mensaje = "Usuario no Encontrado";
        String detalles = ex.getMessage();
        return new Respuesta<>(HttpStatus.UNAUTHORIZED, null, mensaje, detalles);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Contenido<String>> handleExpiredJwtException(ExpiredJwtException ex) {
        String mensaje = "El token JWT ha expirado";
        String detalles = ex.getMessage();
        return new Respuesta<>(HttpStatus.UNAUTHORIZED, null, mensaje, detalles);
    }
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Respuesta<String> handleNullPointerException(NullPointerException ex) {
        String mensaje = "Error interno del servidor";
        String detalles = "NullPointerException: " + ex.getMessage();
        return new Respuesta<>(HttpStatus.INTERNAL_SERVER_ERROR, null, mensaje, detalles);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Respuesta<String> handleAccessDeniedException(AccessDeniedException ex) {
        String mensaje = "Se ha denegado el acceso a esta funcion";
        String detalles = ex.getMessage();
        return new Respuesta<>(HttpStatus.FORBIDDEN, null, mensaje, detalles);
    }

    @ExceptionHandler({DataAccessException.class, JpaSystemException.class, ConstraintViolationException.class})
    public ResponseEntity<Contenido<String>> handleDataAccessException(Exception ex) {
        String mensaje = ex instanceof ConstraintViolationException ? "Violación de restricción de base de datos" : "Error en la capa de persistencia";
        String detalles = ex.getMessage();
        HttpStatus status = ex instanceof ConstraintViolationException ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return new Respuesta<>(status, null, mensaje, detalles);
    }



    private String obtenerDetallesDeValidacion(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder detalles = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {

            detalles.append(fieldError.getField()).append(" ").append(fieldError.getDefaultMessage()).append(". \n");
        }
        return detalles.toString();
    }
}



