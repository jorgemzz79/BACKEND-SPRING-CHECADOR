package com.backend.APIRest.model.dto.jcas;


public class ExcepcionJcas extends RuntimeException {
    private final TiposRespuesta tipo;

    public ExcepcionJcas(TiposRespuesta tipo) {
        super(tipo.getDescripcion());
        this.tipo = tipo;
    }

    public TiposRespuesta getTipo() {
        return tipo;
    }
}