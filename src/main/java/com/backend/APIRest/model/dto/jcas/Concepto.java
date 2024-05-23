package com.backend.APIRest.model.dto.jcas;

import com.backend.APIRest.utils.convertidor.Conversion;
import lombok.*;

@Data
public class Concepto {
    private int idConcepto;
    private String descConcepto;
    private int tasaIVA;
    private double adeudo;
    private double ivaConcepto;
    private boolean cobraIVA;

    public Double getAdeudo() {
        return Conversion.redondearDosDecimales(this.adeudo);
    }

    public Double getIvaConcepto() {
        return Conversion.redondearDosDecimales(this.ivaConcepto);
    }



}