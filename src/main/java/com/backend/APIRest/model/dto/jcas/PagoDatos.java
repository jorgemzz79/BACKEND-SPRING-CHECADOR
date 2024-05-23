package com.backend.APIRest.model.dto.jcas;

import lombok.*;

@Data
public class PagoDatos {
    private int usuario;
    private String password;
    private String identificadorCaja;
    private String cuenta;
    private double monto;
}