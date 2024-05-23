package com.backend.APIRest.model.dto.jcas;

import java.util.List;
import lombok.*;

@Data
public class AbonoConcepto {
    private double montoAbonado;
    private double subTotal;
    private double iva;
    private List<Concepto> conceptosAbonados;
    private SaldoDatos nuevoAdeudo;
}