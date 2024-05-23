package com.backend.APIRest.model.dto.response.respuesta;

import com.backend.APIRest.controlller.UsuarioController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Respuesta<T> extends ResponseEntity<Contenido<T>> {


    public Respuesta(HttpStatus httpStatus,T datos, String mensaje, String detalles) {
        super(new Contenido<>(datos, mensaje, detalles), httpStatus);

    }
}