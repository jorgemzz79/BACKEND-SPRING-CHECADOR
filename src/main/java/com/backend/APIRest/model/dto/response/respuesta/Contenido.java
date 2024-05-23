package com.backend.APIRest.model.dto.response.respuesta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.backend.APIRest.utils.EndpointContextHolder;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
public class Contenido<T>
{
    private T datos;
    private String mensaje;
    private String detalles;

    public Contenido(T datos, String mensaje , String detalles) {
        this.mensaje = mensaje;
        this.datos = datos;
        this.detalles = detalles;
        generarRegistroEnLogger();
    }

    private void generarRegistroEnLogger() {
        Logger logger = LoggerFactory.getLogger(Contenido.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usuario = (authentication != null) ? authentication.getName() : "Anónimo";


        String bitacora = "\n" +
                "---------------------------------------------------------------------------------" + "\n" +
                "--->Endpoint    : " + EndpointContextHolder.getEndpoint() + "\n" +
                "--->Usuario     : " + usuario + "\n" +
                "--->Mensaje     : " + mensaje + "\n" +
                "--->Detalles    : " + detalles + "\n" +
                "---------------------------------------------------------------------------------";
        logger.info(bitacora);
    }
}