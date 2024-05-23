package com.backend.APIRest.model.dto.jcas;
import lombok.*;
@Getter
@Setter
public class Respuesta {

    private int idRespuesta;
    private String descRespuesta;

    public Respuesta(TiposRespuesta tiposRespuesta)
    {
        setIdRespuesta(tiposRespuesta.getId());
        setDescRespuesta(tiposRespuesta.getDescripcion());
    }
}



