package com.backend.APIRest.model.dto.jcas;

import lombok.Data;
public enum TiposRespuesta {
    OK(0, "Ok"),
    EXCEPCION_DEL_SISTEMA(1, "Excepción Del Sistema"),
    ERROR_DE_AUTENTICACION(2, "Error De Autenticación"),
    CUENTA_NO_ENCONTRADA(3, "Cuenta No Encontrada"),
    SALDO_EN_CERO(4, "Saldo En Cero, No Se Puede Realizar El Pago"),
    NO_HAY_TICKET_REGISTRADO(5, "No Hay Un Ticket Registrado Con Esta Cuenta el Día De Hoy"),
    MONTO_NO_VALIDO(6, "Monto No Válido"),
    CUENTA_BLOQUEADA(7, "Cuenta Bloqueada Favor De Pagar en Oficina");

    private final int id;
    private final String descripcion;

    private TiposRespuesta(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}