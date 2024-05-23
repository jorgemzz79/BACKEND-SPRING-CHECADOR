package com.backend.APIRest.model.entidades.comercial;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DatosReciboView implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;

    @Size(min = 10, max = 10, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    String cuenta;

    @Size(min = 3, max = 100, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=false , unique=false)
    String nombre;

    @Size(min = 3, max = 100, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=false , unique=false)
    String direccion;

    @Size(min = 3, max = 100, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=false , unique=false)
    String colonia;

    @Size(min = 3, max = 3, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=false , unique=false)
    String tarifa;

    @Size(min = 3, max = 50, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "nombre_tarifa",nullable=false , unique=false)
    String nombreTarifa;


    @Size(min = 3, max = 50, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "tipo_tarifa",nullable=false , unique=false)
    String tipoTarifa;

    @Size(min = 0, max = 100, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=true , unique=false)
    String giro;

    @Column(name = "fecha_alta",nullable=false , unique=false)
    LocalDate fechaAlta;


    @Size(min = 0, max = 30, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=true , unique=false)
    String telefono;

    @Size(min = 0, max = 30, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=true , unique=false)
    String email;

    @Column(name = "fecha_ultimo_pago",nullable=false , unique=false)
    LocalDate fechaUltimoPago;

    @Size(min = 0, max = 30, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "no_medidor",nullable=true , unique=false)
    String noMedidor;

    @Size(min = 0, max = 50, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "marca_medidor",nullable=true , unique=false)
    String marcaMedidor;

    @Size(min = 0, max = 30, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "diametro_medidor",nullable=true , unique=false)
    String diametroMedidor;


    @Size(min = 0, max = 100, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "tipo_descuento",nullable=true , unique=false)
    String tipoDescuento;

    @Column(name = "caducidad_descuento",nullable=true , unique=false)
    LocalDateTime caducidad_descuento;

    @Size(min = 6, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "ultimo_periodo_facturado",nullable=true , unique=false)
    String ultimoPeriodoFacturado;

    @Size(min = 1, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "lectura_anterior",nullable=true , unique=false)
    Integer lecturaAnterior;

    @Size(min = 1, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "lectura_Actual",nullable=true , unique=false)
    Integer lecturaActual;

    @Size(min = 1, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "consumo_facturado",nullable=true , unique=false)
    Integer consumoFacturado;

    @Size(min = 1, max = 15, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "facturacion_del_mes",nullable=true , unique=false)
    Double facturacionDelMes;

    @Column(nullable=false , unique=false)
    LocalDate vencimiento;

    @Size(min = 1, max = 15, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "anticipo_ejercido",nullable=true , unique=false)
    Double anticipoEjercido;

    @Size(min = 6, max = 255, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=true , unique=false)
    String observacion;

    @Size(min = 6, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "peri_ant1",nullable=true , unique=false)
    String periAnt1;

    @Size(min = 1, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "consumo_ant1",nullable=true , unique=false)
    Integer consumoAnt1;

    @Size(min = 6, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "peri_ant2",nullable=true , unique=false)
    String periAnt2;

    @Size(min = 1, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "consumo_ant2",nullable=true , unique=false)
    Integer consumoAnt2;

    @Size(min = 6, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "peri_ant3",nullable=true , unique=false)
    String periAnt3;

    @Size(min = 1, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "consumo_ant3",nullable=true , unique=false)
    Integer consumoAnt3;

    @Size(min = 1, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "id_convenio",nullable=true , unique=false)
    Integer idConvenio;

    @Column(name = "fecha_convenio",nullable=true , unique=false)
    LocalDate fechaConvenio;

    @Size(min = 1, max = 15, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "deuda_convenio",nullable=true , unique=false)
    Double deudaConvenio;

    @Size(min = 1, max = 6, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=true , unique=false)
    Integer concepto;

    @Size(min = 6, max = 50, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "concepto_nombre",nullable=true , unique=false)
    String conceptoNombre;

    @Size(min = 6, max = 50, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "concepto_tipo",nullable=true , unique=false)
    String conceptoTipo;

    @Size(min = 1, max = 15, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=true , unique=false)
    Double adeudo;

    @Size(min = 1, max = 15, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(name = "tasa_iva",nullable=true , unique=false)
    Double tasaIva;

    @Size(min = 1, max = 15, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=true , unique=false)
    Double descuento;


}
