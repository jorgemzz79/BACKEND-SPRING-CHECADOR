package com.backend.APIRest.model.dto.jcas;

import lombok.*;

@Data
public class RespuestaTicket {
    private int numeroTicket;
    private Respuesta respuesta;
    private Usuario usuario;
    private Organismo organismo;
    private SaldoDatos saldoDatos;
}