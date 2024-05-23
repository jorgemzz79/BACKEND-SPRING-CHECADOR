package com.backend.APIRest.model.entidades.checador;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parametros")
public class Parametro implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable=false , unique=true)
    @Size(min = 5, max = 50, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    String nombre;

    @Size(min = 5, max = 255, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    String descripcion;
    public enum TIPO_DATO { TEXTO, ENTERO, DOUBLE,BOOLEAN,IMAGEN; }
    @Column(name = "tipo_dato",nullable=false)
    @Enumerated(EnumType.STRING)
    private TIPO_DATO tipoDato;

    @Size(min = 1, max = 255, message = "'${validatedValue}' debe contener entre {min} y {max} caracteres de longitud" )
    @Column(nullable=false , unique=false)
    String valor;

}

