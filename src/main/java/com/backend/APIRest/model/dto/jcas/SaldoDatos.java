package com.backend.APIRest.model.dto.jcas;

import java.util.List;

import com.backend.APIRest.utils.convertidor.Conversion;
import lombok.*;

@Data
public class SaldoDatos {
    private double saldo;
    private String nombreUsuario;
    private String direccionUsuario;
    private String cuenta;
    private double subTotal;
    private double iva;
    private int noConvenio;
    private Respuesta respuesta;
    private List<Concepto> conceptos;
    private boolean tieneInsen;
    private boolean tieneSubsidio;

    public double getSaldo() {
        return Conversion.redondearDosDecimales(this.saldo);
    }
    public double getSubtotal() {
        return Conversion.redondearDosDecimales(this.subTotal);
    } public double getIva() {
        return Conversion.redondearDosDecimales(this.iva);
    }
    // Setter que acepta un objeto Respuesta
    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    // Setter que acepta un objeto TipoRespuesta y crea una Respuesta a partir de él
    public void setRespuesta(TiposRespuesta tipoRespuesta) {
        this.respuesta = new Respuesta(tipoRespuesta);
    }
}