package com.backend.APIRest.model.dto.jcas;

import lombok.*;

@Data
public class RespuestaAbono {
    private int numeroTicket;
    private Respuesta respuesta;
    private Usuario usuario;
    private Organismo organismo;
    private AbonoConcepto abonoAplicado;
}