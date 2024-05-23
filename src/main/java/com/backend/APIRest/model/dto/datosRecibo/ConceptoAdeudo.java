package com.backend.APIRest.model.dto.datosRecibo;


import com.backend.APIRest.model.entidades.comercial.DatosReciboView;
import com.backend.APIRest.utils.convertidor.Conversion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConceptoAdeudo {
    Integer concepto;
    String conceptoNombre;
    String tipo;
    Double adeudo;
    Double tasaIva;
    Double descuento;

    public ConceptoAdeudo(DatosReciboView datosReciboView) {

        this.tipo = datosReciboView.getConceptoTipo();
        this.concepto = datosReciboView.getConcepto();
        this.conceptoNombre = datosReciboView.getConceptoNombre();
        this.adeudo = datosReciboView.getAdeudo();
        this.tasaIva = datosReciboView.getTasaIva();
        this.descuento = datosReciboView.getDescuento();
    }

    // Getter modificado para devolver el valor de adeudo redondeado a dos decimales
    public Double getAdeudo() {
        return Conversion.redondearDosDecimales(this.adeudo);
    }

    // Getter modificado para devolver el valor de tasaIva redondeado a dos decimales
    public Double getTasaIva() {
        return Conversion.redondearDosDecimales(this.tasaIva);
    }

    // Getter modificado para devolver el valor de descuento redondeado a dos decimales
    public Double getDescuento() {
        return Conversion.redondearDosDecimales(this.descuento);
    }

    // Método para redondear un valor double a dos decimales

}
