package com.backend.APIRest.model.dto.datosRecibo;

import com.backend.APIRest.model.entidades.comercial.DatosReciboView;
import com.backend.APIRest.model.entidades.checador.Parametro;
import com.backend.APIRest.utils.convertidor.Conversion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class DatosRecibo {
    String cuenta;
    String nombre;
    String direccion;
    String colonia;
    String tarifa;


    String nombreTarifa;
    String giro;
    LocalDate fechaAlta;
    String telefono;
    String email;
    LocalDate fechaUltimoPago;
    String noMedidor;
    String marcaMedidor;
    String diametroMedidor;
    String tipoDescuento;
    LocalDateTime caducidadDescuento;
    String ultimoPeriodoFacturado;
    Integer lecturaAnterior;
    Integer lecturaActual;
    Integer consumoFacturado;
    Double facturacionDelMes;
    LocalDate vencimiento;
    Double anticipoEjercido;
    String observacion;

    String periAnt1;
    String mesAnt1;
    Integer consumoAnt1;

    String periAnt2;
    String mesAnt2;
    Integer consumoAnt2;

    String periAnt3;
    String mesAnt3;
    Integer consumoAnt3;

    Integer idConvenio;
    LocalDate fechaConvenio;
    Double deudaConvenio;

    Double ivaAntesDeDescuento;
    Double ivaConDescuento;
    Double subtotalAPagarAntesDeDescuento;
    Double subtotalAPagarConDescuento;
    Double descuentoSocial;

    String codigoDeBarras;

    List<ConceptoAdeudo> conceptos;

    String tipoTarifa;
    Double rzEjActual;
    Double rzEjAnterior;
    Double cargosConvenio;
    Double mesActual;
    Double multas;
    Double recargos;
    Double otros;
    Boolean vencido;
    Boolean paraCorte;
    Boolean imprimirFondo;

    Parametro montoParaVencer;
    Parametro diasSinPagoParaVencer;

    private void Recalcular()
    {
        System.out.println("VOY A CALCULAR");
        DecimalFormat d2  =new DecimalFormat("0.00");
        vencido=false;
        paraCorte=false;
        mesActual=0.0;
        rzEjActual=0.0;
        rzEjAnterior=0.0;
        cargosConvenio=0.0;
        multas=0.0;
        recargos=0.0;
        otros=0.0;

        ivaAntesDeDescuento=0.0;
        ivaConDescuento=0.0;
        subtotalAPagarAntesDeDescuento=0.0;
        subtotalAPagarConDescuento=0.0;
        descuentoSocial=0.0;
        System.out.println("1");
        if(conceptos.size()>0) {

            System.out.println("1.5");
            for (ConceptoAdeudo concepto : conceptos) {
                ivaAntesDeDescuento += concepto.getAdeudo() * concepto.getTasaIva();
                ivaConDescuento += (concepto.getAdeudo() - concepto.getDescuento()) * concepto.getTasaIva();
                subtotalAPagarAntesDeDescuento += concepto.getAdeudo();
                subtotalAPagarConDescuento += (concepto.getAdeudo() - concepto.getDescuento());
                descuentoSocial += concepto.getDescuento();
                switch (concepto.getTipo()) {
                    case "ADS": {
                        //REZAGO
                        if (concepto.getConcepto() < 15)
                            rzEjActual += concepto.getAdeudo();
                        else {
                            if (concepto.getConcepto() > 5000)
                                cargosConvenio += concepto.getAdeudo();
                            else
                                rzEjAnterior += concepto.getAdeudo();

                        }
                        break;
                    }
                    case "AT ADS":
                    case "AT COV": {
                        //MES ACTUAL
                        mesActual += concepto.getAdeudo();
                        break;
                    }
                    case "MULTA": {
                        //MULTAS
                        multas += concepto.getAdeudo();
                        break;
                    }
                    case "RC": {
                        //RECARGOS
                        recargos += concepto.getAdeudo();
                        break;
                    }
                    case "DFEA":
                    case "RECONEXION":
                    case "OTROS":
                    default: {
                        //OTROS
                        if (concepto.getConcepto() == 4)
                            mesActual += concepto.getAdeudo();
                        else
                            otros += concepto.getAdeudo();
                        break;
                    }

                }



            }
        }

        System.out.println("2");
        //Evalua vencido y aCorte
        Double montoParaVencerDouble = Double.valueOf(montoParaVencer.getValor());
        if ((rzEjAnterior + rzEjActual) >= montoParaVencerDouble) {
            vencido = true;
            if (fechaUltimoPago.isBefore(LocalDate.now().minusDays(Long.valueOf(diasSinPagoParaVencer.getValor()))))
                paraCorte = true;
        }
        System.out.println("VOY A HACER EL CODIGO DE BARRAS");
        generarCodigoDeBarras();
        System.out.println("YA ESTA");
    }

    private void generarCodigoDeBarras()
    {
        String cadenaSinDigitoVerificador="01";//SE INICIALIZA CON 01
        cadenaSinDigitoVerificador+=getCuenta(); //CUENTA
        cadenaSinDigitoVerificador+=getVencimiento().getYear();//AÑOVENCIMIENTO
        cadenaSinDigitoVerificador+=String.format("%02d", getVencimiento().getMonthValue());//MESVENCIMIENTO
        cadenaSinDigitoVerificador+=String.format("%02d", getVencimiento().getDayOfMonth());//DIAVENCIMIENTO
        cadenaSinDigitoVerificador+=(subtotalAPagarConDescuento+ivaConDescuento)<1000000?
                String.format("%08d",(int) (Math.round((subtotalAPagarConDescuento+ivaConDescuento)*100))):
                String.format("%08d",0);//ADEUDO

        String digitoVerificador="";
        int multiplicador=1;
        int suma=0;
        for(int posicion=0;posicion<28;posicion++)
        {
            suma+=Integer.parseInt(cadenaSinDigitoVerificador.substring(posicion,posicion+1))*multiplicador;
            multiplicador=multiplicador==9?1:multiplicador+1;
            digitoVerificador=Integer.toString(suma).substring(Integer.toString(suma).length()-1);
        }
        codigoDeBarras= cadenaSinDigitoVerificador+digitoVerificador;
    }

    public DatosRecibo(DatosReciboView datosReciboView, Parametro montoParaVencer, Parametro diasSinPagoParaVencer) {
        this.conceptos = new ArrayList<>();
        this.montoParaVencer=montoParaVencer;
        this.diasSinPagoParaVencer=diasSinPagoParaVencer;
        this.cuenta = datosReciboView.getCuenta();
        this.nombre = datosReciboView.getNombre();
        this.direccion = datosReciboView.getDireccion();
        this.colonia = datosReciboView.getColonia();
        this.tarifa = datosReciboView.getTarifa();
        this.nombreTarifa = datosReciboView.getNombreTarifa();
        this.imprimirFondo=true;
        this.tipoTarifa = datosReciboView.getTipoTarifa();
        this.giro = datosReciboView.getGiro();
        this.fechaAlta = datosReciboView.getFechaAlta();
        this.telefono = datosReciboView.getTelefono();
        this.email = datosReciboView.getEmail();
        this.fechaUltimoPago = datosReciboView.getFechaUltimoPago();
        this.noMedidor = datosReciboView.getNoMedidor();
        this.marcaMedidor = datosReciboView.getMarcaMedidor();
        this.diametroMedidor = datosReciboView.getDiametroMedidor();
        this.tipoDescuento = datosReciboView.getTipoDescuento();
        this.caducidadDescuento = datosReciboView.getCaducidad_descuento();
        this.ultimoPeriodoFacturado = datosReciboView.getUltimoPeriodoFacturado();
        this.lecturaAnterior = datosReciboView.getLecturaAnterior();
        this.lecturaActual = datosReciboView.getLecturaActual();
        this.consumoFacturado = datosReciboView.getConsumoFacturado();
        this.facturacionDelMes = datosReciboView.getFacturacionDelMes();
        this.vencimiento = datosReciboView.getVencimiento();
        this.anticipoEjercido = datosReciboView.getAnticipoEjercido();
        this.observacion = datosReciboView.getObservacion();
        this.periAnt1 = datosReciboView.getPeriAnt1();
        this.consumoAnt1 = datosReciboView.getConsumoAnt1();
        this.periAnt2 = datosReciboView.getPeriAnt2();
        this.consumoAnt2 = datosReciboView.getConsumoAnt2();
        this.periAnt3 = datosReciboView.getPeriAnt3();
        this.consumoAnt3 = datosReciboView.getConsumoAnt3();
        this.idConvenio = datosReciboView.getIdConvenio();
        this.fechaConvenio = datosReciboView.getFechaConvenio();
        this.deudaConvenio = datosReciboView.getDeudaConvenio();
        this.mesAnt1=nombreMes( datosReciboView.getPeriAnt1());
        this.mesAnt2=nombreMes( datosReciboView.getPeriAnt2());
        this.mesAnt3=nombreMes( datosReciboView.getPeriAnt3());
        Recalcular();
    }

    public void AgregarConcepto(DatosReciboView datosReciboView)
    {
        if (this.conceptos == null) {
            this.conceptos = new ArrayList<>(); // Inicialización de la lista si es nula
        }
        conceptos.add(new ConceptoAdeudo(datosReciboView));
        Recalcular();
    }

    private String nombreMes(String periodo)
    {
        Integer numMes=0;
        if(periodo!=null) {
            if(periodo.trim().length()==6) {
                numMes = Integer.parseInt(periodo.trim().substring(4));

                String mes = "";
                switch (numMes) {
                    case 1: {
                        mes = "ENERO";
                        break;
                    }
                    case 2: {
                        mes = "FEBRERO";
                        break;
                    }

                    case 3: {
                        mes = "MARZO";
                        break;
                    }

                    case 4: {
                        mes = "ABRIL";
                        break;
                    }

                    case 5: {
                        mes = "MAYO";
                        break;
                    }

                    case 6: {
                        mes = "JUNE";
                        break;
                    }

                    case 7: {
                        mes = "JULIO";
                        break;
                    }

                    case 8: {
                        mes = "AGOSTO";
                        break;
                    }

                    case 9: {
                        mes = "SEPTIEMBRE";
                        break;
                    }

                    case 10: {
                        mes = "OCTUBRE";
                        break;
                    }

                    case 11: {
                        mes = "NOVIEMBRE";
                        break;
                    }

                    case 12: {
                        mes = "DICIEMBRE";
                        break;
                    }

                }

                return mes;
            }
            else
                return "";

        }
        else
            return "";
    }


    // Getter con redondeo para deudaConvenio
    public Double getDeudaConvenio() {
        return Conversion.redondearDosDecimales(this.deudaConvenio);
    }


    // Getter con redondeo para ivaAntesDeDescuento
    public Double getIvaAntesDeDescuento() {
        return Conversion.redondearDosDecimales(this.ivaAntesDeDescuento);
    }

    // Getter con redondeo para ivaConDescuento
    public Double getIvaConDescuento() {
        return Conversion.redondearDosDecimales(this.ivaConDescuento);
    }

    // Getter con redondeo para subtotalAPagarAntesDeDescuento
    public Double getSubtotalAPagarAntesDeDescuento() {
        return Conversion.redondearDosDecimales(this.subtotalAPagarAntesDeDescuento);
    }

    // Getter con redondeo para subtotalAPagarConDescuento
    public Double getSubtotalAPagarConDescuento() {
        return Conversion.redondearDosDecimales(this.subtotalAPagarConDescuento);
    }

    // Getter con redondeo para descuentoSocial
    public Double getDescuentoSocial() {
        return Conversion.redondearDosDecimales(this.descuentoSocial);
    }

}
